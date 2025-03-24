package com.quodex.JobSpark.controller;


import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.dto.ResponseDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable Long id) throws JobSparkException {
        return new ResponseEntity<>(profileService.getProfile(id), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO profileDTO) throws JobSparkException{
        return new ResponseEntity<>(profileService.updateProfile(profileDTO), HttpStatus.OK);
    }
}
