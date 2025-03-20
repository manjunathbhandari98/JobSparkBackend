package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.LoginDTO;
import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.entity.User;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.UserRepository;
import com.quodex.JobSpark.utility.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) throws JobSparkException {
        // Check if User already exist
        Optional<User> email = userRepository.findByEmail(userDTO.getEmail());
        if(email.isPresent()) throw new JobSparkException("USER_FOUND");
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



}