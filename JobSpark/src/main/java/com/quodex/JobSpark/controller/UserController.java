package com.quodex.JobSpark.controller;

import com.quodex.JobSpark.dto.LoginDTO;
import com.quodex.JobSpark.dto.ResponseDTO;
import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.entity.OTP;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin
@Validated
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) throws JobSparkException {
        userDTO = userService.registerUser(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> loginUser(@RequestBody @Valid LoginDTO loginDTO) throws JobSparkException {
        // Call service method to authenticate user and get UserDTO
        UserDTO userDTO = userService.loginUser(loginDTO);

        // Return authenticated user details
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/sendOTP/{email}")
    public ResponseEntity<ResponseDTO> sendOTP(@PathVariable @Email(message = "{user.email.invalid}") String email) throws JobSparkException, MessagingException {

        userService.sendOTP(email);

        // Return authenticated user details
        return new ResponseEntity<>(new ResponseDTO("OTP Sent Successfully"), HttpStatus.OK);
    }

    @GetMapping("/verifyOTP/{email}/{otp}")
    public ResponseEntity<ResponseDTO> verifyOTP(@PathVariable @Email(message = "{user.email.invalid}") String email, @PathVariable  String otp) throws JobSparkException {

        userService.verifyOTP(email,otp);

        // Return authenticated user details
        return new ResponseEntity<>(new ResponseDTO("OTP Verified Successfully"), HttpStatus.OK);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseDTO> resetPassword(@RequestBody @Valid LoginDTO loginDTO) throws JobSparkException{
        return new ResponseEntity<>(userService.resetPassword(loginDTO), HttpStatus.OK);
    }

}
