package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.entity.Profile;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.ProfileRepository;
import com.quodex.JobSpark.utility.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepository;
    @Override
    public Long createProfile(String email) throws JobSparkException {

            Profile profile = new Profile();
            profile.setId(Utilites.getNextSequence("profiles"));
            profile.setEmail(email);
            profile.setSkills(new ArrayList<>());
            profile.setExperiences(new ArrayList<>());
            profile.setCertificates(new ArrayList<>());
            profileRepository.save(profile);
            return profile.getId();

    }

    @Override
    public ProfileDTO getProfile(Long id) throws JobSparkException {
        return profileRepository.findById(id).orElseThrow(()-> new JobSparkException("PROFILE_NOT_FOUND")).toDTO();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobSparkException {
        profileRepository.findById(profileDTO.getId()).orElseThrow(()-> new JobSparkException("PROFILE_NOT_FOUND")).toDTO();
        profileRepository.save(profileDTO.toEntity());
        return profileDTO;
    }

    @Override
    public List<ProfileDTO> getAllProfile() throws JobSparkException {
        return profileRepository.findAll().stream().map(Profile::toDTO).toList();
    }


}
