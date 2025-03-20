package com.quodex.JobSpark.controller;

import com.quodex.JobSpark.dto.LoginDTO;
import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.UserService;
import jakarta.validation.Valid;
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

}
