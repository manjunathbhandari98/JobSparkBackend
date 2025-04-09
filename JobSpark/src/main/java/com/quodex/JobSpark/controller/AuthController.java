package com.quodex.JobSpark.controller;

import com.quodex.JobSpark.jwt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtHelper.generateToken(userDetails);

        // Extract details from CustomUserDetails
        CustomUserDetails customUser = (CustomUserDetails) userDetails;

        return ResponseEntity.ok(
                new AuthResponse(
                        jwt,
                        customUser.getId(),
                        customUser.getUsername(),
                        customUser.getName(),
                        customUser.getAccountType().toString()
                )
        );
    }

}
