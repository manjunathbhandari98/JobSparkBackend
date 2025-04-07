package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ApplicantDTO;
import com.quodex.JobSpark.dto.JobDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import org.springframework.http.HttpStatusCode;

import java.util.List;

public interface JobService {

    List<JobDTO> getAllJobs() throws JobSparkException;

    JobDTO getJobById(Long id) throws JobSparkException;

    JobDTO postJob(JobDTO jobDTO) throws JobSparkException;


    void deleteJobById(Long id);

    JobDTO updateJob(Long id, JobDTO jobDTO) throws JobSparkException;

    ApplicantDTO applyJob(Long id, ApplicantDTO applicantDTO) throws JobSparkException;

    List<JobDTO> getJobPosterById(Long id) throws JobSparkException;

    JobDTO updateApplicantStatus(Long jobId, Long applicantId, ApplicantDTO updatedApplicant) throws JobSparkException;
}
