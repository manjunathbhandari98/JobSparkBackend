package com.quodex.JobSpark.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.dto.ResponseDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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

    @GetMapping()
    public ResponseEntity<List<ProfileDTO>> getAllProfiles() throws JobSparkException{
        return new ResponseEntity<>(profileService.getAllProfile(), HttpStatus.OK);
    }


    @PutMapping("/update-info")
    public ResponseEntity<ProfileDTO> updateProfileInfo(@RequestBody ProfileDTO profileDTO) throws JobSparkException {
        ProfileDTO updated = profileService.updateProfile(profileDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update-image")
    public ResponseEntity<ProfileDTO> updateProfileImage(@RequestParam("userId") Long userId,
                                                         @RequestPart(value="image", required = false) MultipartFile imageFile) throws JobSparkException {
        ProfileDTO updated = profileService.updateProfileImage(userId, imageFile);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
