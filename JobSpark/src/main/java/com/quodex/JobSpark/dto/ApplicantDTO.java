package com.quodex.JobSpark.dto;

import com.quodex.JobSpark.entity.Applicant;

import java.time.LocalDateTime;
import java.util.Base64;

public class ApplicantDTO {
    private Long applicantId;
    private String name;
    private String email;
    private Long phone;
    private String website;
    private String resume;
    private String coverLetter;
    private LocalDateTime timeStamp;
    private ApplicationStatus applicationStatus;
    private LocalDateTime interviewTime;

    public ApplicantDTO(Long applicantId, String name, String email, Long phone, String website,
                        String resume, String coverLetter, LocalDateTime timeStamp, ApplicationStatus applicationStatus
    , LocalDateTime interviewTime) {
        this.applicantId = applicantId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.resume = resume;
        this.coverLetter = coverLetter;
        this.timeStamp = timeStamp;
        this.applicationStatus = applicationStatus;
        this.interviewTime = interviewTime;
    }

    public ApplicantDTO(){}

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public LocalDateTime getInterviewTime() {
        return interviewTime;
    }

    public void setInterviewTime(LocalDateTime interviewTime) {
        this.interviewTime = interviewTime;
    }

    public Applicant toEntity(){
        return new Applicant(
                this.applicantId,
                this.name,
                this.email,
                this.phone,
                this.website,
                this.resume!= null ? Base64.getDecoder().decode(this.resume) : null,
                this.coverLetter,
                this.timeStamp,
                this.applicationStatus,
                this.interviewTime);
    }
}
