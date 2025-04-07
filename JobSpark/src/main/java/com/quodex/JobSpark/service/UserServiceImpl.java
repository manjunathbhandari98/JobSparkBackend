package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.LoginDTO;
import com.quodex.JobSpark.dto.NotificationsDTO;
import com.quodex.JobSpark.dto.ResponseDTO;
import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.entity.OTP;
import com.quodex.JobSpark.entity.User;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.NotificationRepository;
import com.quodex.JobSpark.repository.OtpRepository;
import com.quodex.JobSpark.repository.UserRepository;
import com.quodex.JobSpark.utility.Data;
import com.quodex.JobSpark.utility.OTPGenerator;
import com.quodex.JobSpark.utility.Utilites;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private NotificationService notificationService;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobSparkException {
        // Check if User already exist
        Optional<User> email = userRepository.findByEmail(userDTO.getEmail());
        if(email.isPresent()) throw new JobSparkException("USER_FOUND");

        // set profileId using email
        userDTO.setProfileId(profileService.createProfile(userDTO.getEmail()));

        // Set id in sequence (using getNextSequence) method
        userDTO.setId(Utilites.getNextSequence("users"));

        // Encode the password BEFORE converting to entity
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassword);

        // Convert DTO to Entity (now it has the encoded password)
        User user = userDTO.toEntity();

        // Save User Entity in Database
        User savedUser = userRepository.save(user);

        // Convert Saved Entity back to DTO and return
        return savedUser.toDto();
    }

    @Override
    public UserDTO loginUser(LoginDTO loginDTO) throws JobSparkException {
        // Find user by email or throw an exception if not found
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobSparkException("USER_NOT_FOUND"));

        // Compare raw password with the encoded password stored in the database
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new JobSparkException("INVALID_CREDENTIALS");
        }


        // Convert User entity to DTO and return
        return user.toDto();

    }

    @Override
    public void sendOTP(String email) throws JobSparkException, MessagingException {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new JobSparkException("USER_NOT_FOUND"));
        // Create a new MIME email message
        MimeMessage mm = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mm, true); // Enable multipart messages

        // Set recipient email address
        message.setTo(email);

        // Set email subject
        message.setSubject("Your OTP");

        // Generate a one-time password (OTP)
        String generatedOtp = OTPGenerator.generateOTP();

        // Create an OTP object with the generated OTP, user's email, and the current timestamp
        OTP otp = new OTP(email, generatedOtp, LocalDateTime.now());

        // Save the OTP in the repository (presumably for validation later)
        otpRepository.save(otp);

        // Set email body with the generated OTP
        message.setText(Data.getMessageBody(generatedOtp,user.getName()) , true); // 'false' indicates plain text format

        // Send the email
        mailSender.send(mm);

    }

    @Override
    public void verifyOTP(String email, String otp) throws JobSparkException {
        // Retrieve the OTP entity from the repository using the email
        // If not found, throw an exception indicating an invalid OTP
        OTP otpEntity = otpRepository.findById(email)
                .orElseThrow(() -> new JobSparkException("INVALID_OTP"));

        // Check if the provided OTP matches the stored OTP
        if (!otpEntity.getOtpCode().equals(otp)) {
            throw new JobSparkException("INVALID_OTP"); // Throw exception if OTP is incorrect
        }

        // OTP is valid; proceed with further processing if needed
    }

    @Override
    public ResponseDTO resetPassword(LoginDTO loginDTO) throws JobSparkException {
        // Retrieve the user by email from the repository
        // If the user is not found, throw an exception
        User user = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new JobSparkException("USER_NOT_FOUND"));

        // Encode the new password before saving it to the database for security
        user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));

        // Save the updated user entity with the new password
        userRepository.save(user);
        NotificationsDTO notificationsDTO = new NotificationsDTO();
        notificationsDTO.setUserId(user.getId());
        notificationsDTO.setMessage("Password Reset Successfully");
        notificationsDTO.setAction("Password Reset");
        notificationService.sendNotification(notificationsDTO);
        // Return a response indicating success
        return new ResponseDTO("Password Changed Successfully");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(User::toDto).toList();
    }


    // Method to delete the OTP after 10 minutes
    @Scheduled(fixedRate = 60000) // Runs every 60 seconds (1 minute)
    public void removeExpiredOtps() {
        // Define the expiration threshold (10 minutes ago from now)
        LocalDateTime expiry = LocalDateTime.now().minusMinutes(10);

        // Retrieve all OTPs that were created before the expiration time
        List<OTP> expiredOTPs = otpRepository.findByCreationTimeBefore(expiry);

        // If there are expired OTPs, remove them from the database
        if (!expiredOTPs.isEmpty()) {
            otpRepository.deleteAll(expiredOTPs);
        }
    }

}