package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ProfileDTO;
import com.quodex.JobSpark.entity.Profile;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.ProfileRepository;
import com.quodex.JobSpark.utility.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service("profileService")
public class ProfileServiceImpl implements ProfileService{

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private FileUploadService fileUploadService;

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
    public List<ProfileDTO> getAllProfile() throws JobSparkException {
        return profileRepository.findAll().stream().map(Profile::toDTO).toList();
    }

    @Override
    public ProfileDTO updateProfile(ProfileDTO profileDTO) throws JobSparkException {
        Profile existing = profileRepository.findById(profileDTO.getId())
                .orElseThrow(() -> new JobSparkException("PROFILE_NOT_FOUND"));

        // Update only the relevant fields
        existing.setAbout(profileDTO.getAbout());
        existing.setJobTitle(profileDTO.getJobTitle());
        existing.setSkills(profileDTO.getSkills());
        existing.setCompany(profileDTO.getCompany());
        existing.setLocation(profileDTO.getLocation());
        existing.setTotalExperience(profileDTO.getTotalExperience());
        existing.setExperiences(profileDTO.getExperiences());
        existing.setCertificates(profileDTO.getCertificates());
        existing.setEmail(profileDTO.getEmail());


        Profile saved = profileRepository.save(existing);
        return saved.toDTO();
    }


    @Override
    public ProfileDTO updateProfileImage(Long userId, MultipartFile imageFile) throws JobSparkException {
        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> new JobSparkException("User not found"));

            String uploadedUrl = fileUploadService.uploadFile(imageFile); // Assume this returns the uploaded image URL
            profile.setPicture(uploadedUrl);


        return profileRepository.save(profile).toDTO();

    }



}
