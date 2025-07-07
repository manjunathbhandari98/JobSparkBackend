package com.quodex.JobSpark.entity;

import com.quodex.JobSpark.dto.Certificate;
import com.quodex.JobSpark.dto.Experience;
import com.quodex.JobSpark.dto.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Base64;
import java.util.List;

@Document(collection = "profiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {

    @Id
    private Long id;
    private String email;
    private String jobTitle;
    private String company;
    private String location;
    private String about;
    private String picture;
    private List<String> skills;
    private List<Experience> experiences;
    private List<Certificate> certificates;
    private List<Long> savedJobs;
    private Long totalExperience;


    public ProfileDTO toDTO() {
        return new ProfileDTO(
                this.id,
                this.email,
                this.jobTitle,
                this.company,
                this.location,
                this.about,
                this.skills,
                this.experiences,
                this.certificates,
                this.savedJobs,
                this.totalExperience,
                this.picture
        );
    }

}
