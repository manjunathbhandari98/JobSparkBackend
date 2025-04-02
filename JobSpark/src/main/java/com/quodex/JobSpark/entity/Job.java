package com.quodex.JobSpark.entity;

import com.quodex.JobSpark.dto.JobDTO;
import com.quodex.JobSpark.dto.JobStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "jobs")
public class Job {
    @Id
    private Long id;
    private String jobTitle;
    private String company;
    private List<Applicant> applicants;
    private String about;
    private String experience;
    private String jobType;
    private String location;
    private String packageOffered;
    private LocalDateTime postTime;
    private String description;
    private List<String> skillsRequired;
    private JobStatus jobStatus;
    private Long postedBy;
    public Job(Long id, String jobTitle, String company,
               List<Applicant> applicants, String about,
               String experience, String jobType, String location,
               String packageOffered, LocalDateTime postTime,
               String description, List<String> skillsRequired,
               JobStatus jobStatus, Long postedBy) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.company = company;
        this.applicants = applicants;
        this.about = about;
        this.experience = experience;
        this.jobType = jobType;
        this.location = location;
        this.packageOffered = packageOffered;
        this.postTime = postTime;
        this.description = description;
        this.skillsRequired = skillsRequired;
        this.jobStatus = jobStatus;
        this.postedBy = postedBy;
    }

    public Job(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Applicant> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Applicant> applicants) {
        this.applicants = applicants;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPackageOffered() {
        return packageOffered;
    }

    public void setPackageOffered(String packageOffered) {
        this.packageOffered = packageOffered;
    }

    public LocalDateTime getPostTime() {
        return postTime;
    }

    public void setPostTime(LocalDateTime postTime) {
        this.postTime = postTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(List<String> skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Long getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(Long postedBy) {
        this.postedBy = postedBy;
    }

    public JobDTO toDTO(){
        return new JobDTO(
                this.id,
                this.jobTitle,
                this.company,
                this.applicants != null ? this.applicants.stream().map(x->x.toDTO()).toList():null,
                this.about,
                this.experience,
                this.jobType,
                this.location,
                this.packageOffered,
                this.postTime,
                this.description,
                this.skillsRequired,
                this.jobStatus,
                this.postedBy
        );
    }
}
