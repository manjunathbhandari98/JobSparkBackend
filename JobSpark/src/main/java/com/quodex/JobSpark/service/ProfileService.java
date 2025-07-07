package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProfileService {

    Long createProfile(String email) throws JobSparkException;

    public ProfileDTO getProfile(Long id) throws JobSparkException;
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobSparkException;

    List<ProfileDTO> getAllProfile() throws JobSparkException;

    ProfileDTO updateProfileImage(Long userId, MultipartFile imageFile) throws JobSparkException;

}
