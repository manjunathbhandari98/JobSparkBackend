package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ApplicantDTO;
import com.quodex.JobSpark.dto.ApplicationStatus;
import com.quodex.JobSpark.dto.JobDTO;
import com.quodex.JobSpark.entity.Applicant;
import com.quodex.JobSpark.entity.Job;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.JobRepository;
import com.quodex.JobSpark.utility.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private JobRepository jobRepository;

    /**
     * Retrieves all jobs from the repository and converts them to DTOs.
     *
     * @return List of JobDTOs
     * @throws JobSparkException if an error occurs while fetching jobs
     */
    @Override
    public List<JobDTO> getAllJobs() throws JobSparkException {
        return jobRepository.findAll()
                .stream()
                .map(Job::toDTO) // Convert each Job entity to JobDTO
                .toList();
    }

    /**
     * Retrieves a job by its ID.
     *
     * @param id Job ID
     * @return JobDTO object
     * @throws JobSparkException if job not found
     */
    @Override
    public JobDTO getJobById(Long id) throws JobSparkException {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"))
                .toDTO();
    }

    /**
     * Posts a new job.
     *
     * @param jobDTO Job data to be saved
     * @return Saved JobDTO
     * @throws JobSparkException if an error occurs
     */
    @Override
    public JobDTO postJob(JobDTO jobDTO) throws JobSparkException {
        jobDTO.setId(Utilites.getNextSequence("jobs")); // Assign a unique job ID
        jobDTO.setPostTime(LocalDateTime.now()); // Set posting time
        return jobRepository.save(jobDTO.toEntity()).toDTO(); // Convert DTO to entity, save, and convert back to DTO
    }

    /**
     * Deletes a job by its ID.
     *
     * @param id Job ID
     */
    @Override
    public void deleteJobById(Long id) {
        jobRepository.deleteById(id);
    }

    /**
     * Updates an existing job.
     *
     * @param id Job ID
     * @param jobDTO Updated job data
     * @return Updated JobDTO
     * @throws JobSparkException if job not found
     */
    @Override
    public JobDTO updateJob(Long id, JobDTO jobDTO) throws JobSparkException {
        // Ensure the job exists before updating
        jobRepository.findById(id).orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"));

        // Save the updated job data
        jobRepository.save(jobDTO.toEntity());
        return jobDTO;
    }

    /**
     * Allows an applicant to apply for a job.
     *
     * @param id Job ID
     * @param applicantDTO Applicant data
     * @return ApplicantDTO with application status
     * @throws JobSparkException if job not found or applicant already applied
     */
    @Override
    public ApplicantDTO applyJob(Long id, ApplicantDTO applicantDTO) throws JobSparkException {
        // Fetch the job entity or throw an exception if not found
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"));

        // Get the list of applicants (initialize if null)
        List<Applicant> applicants = job.getApplicants();
        if (applicants == null) {
            applicants = new ArrayList<>();
        }

        // Check if the applicant has already applied for the job
        boolean alreadyApplied = applicants.stream()
                .anyMatch(x -> x.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new JobSparkException("JOB_APPLIED_ALREADY");
        }

        // Set application status and add applicant to the list
        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicantDTO.setTimeStamp(LocalDateTime.now());
        applicants.add(applicantDTO.toEntity());
        // Update the job entity with the new applicant
        job.setApplicants(applicants);
        jobRepository.save(job);

        return applicantDTO;
    }

    @Override
    public List<JobDTO> getJobPosterById(Long id) throws JobSparkException {
        return jobRepository.findPosterById(id)
                .stream()
                .map(Job::toDTO) // Convert each Job entity to JobDTO
                .toList();
    }
}
