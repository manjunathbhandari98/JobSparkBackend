package com.quodex.JobSpark.dto;

import com.quodex.JobSpark.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDTO {
    private Long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;
    private List<Long> savedJobs;
    private Long totalExperience;
    private String picture;

    public Profile toEntity() {
        return Profile.builder()
                .id(this.id)
                .email(this.email)
                .jobTitle(this.jobTitle)
                .company(this.company)
                .location(this.location)
                .about(this.about)
                .skills(this.skills)
                .experiences(this.experiences)
                .certificates(this.certificates)
                .savedJobs(this.savedJobs)
                .totalExperience(this.totalExperience)
                .build();

    }
}
