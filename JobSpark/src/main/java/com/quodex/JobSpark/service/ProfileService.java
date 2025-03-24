package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.exception.JobSparkException;

public interface ProfileService {

    Long createProfile(String email) throws JobSparkException;

    public ProfileDTO getProfile(Long id) throws JobSparkException;
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobSparkException;

}
