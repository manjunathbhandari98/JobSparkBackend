package com.quodex.JobSpark.jwt;

import com.quodex.JobSpark.dto.UserDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UserDTO userDTO = userService.getUserByEmail(username);
            return new CustomUserDetails(userDTO.getId(), username, userDTO.getName()
                    ,userDTO.getPassword(), userDTO.getAccountType(), new ArrayList<>());
        } catch (JobSparkException e) {
            throw new RuntimeException(e);
        }
    }
}
