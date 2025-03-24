package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.LoginDTO;
import com.quodex.JobSpark.dto.ResponseDTO;
import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import jakarta.mail.MessagingException;

/**
 * Service interface for managing user-related operations.
 * Defines methods to be implemented by the service class.
 */
public interface UserService {
    public UserDTO registerUser(UserDTO userDTO) throws JobSparkException;

    UserDTO loginUser(LoginDTO loginDTO) throws JobSparkException;

    void sendOTP(String email) throws JobSparkException, MessagingException;

    void verifyOTP(String email, String otp) throws JobSparkException;

    ResponseDTO resetPassword(LoginDTO loginDTO) throws JobSparkException;
}
