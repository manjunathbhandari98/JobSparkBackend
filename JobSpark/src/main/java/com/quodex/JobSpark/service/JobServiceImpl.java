package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.ApplicantDTO;
import com.quodex.JobSpark.dto.ApplicationStatus;
import com.quodex.JobSpark.dto.JobDTO;
import com.quodex.JobSpark.dto.NotificationsDTO;
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
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private NotificationService notificationService;

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
                .map(Job::toDTO)
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
        jobDTO.setId(Utilites.getNextSequence("jobs"));
        jobDTO.setPostTime(LocalDateTime.now());
        return jobRepository.save(jobDTO.toEntity()).toDTO();
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
        jobRepository.findById(id)
                .orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"));

        Job job = jobRepository.save(jobDTO.toEntity());

        if (job.getApplicants() != null) {
            for (Applicant applicant : job.getApplicants()) {
                if (applicant.getApplicationStatus() == ApplicationStatus.INTERVIEWING) {
                    // Set interview time if not already set
                    if (applicant.getInterviewTime() == null) {
                        applicant.setInterviewTime(LocalDateTime.now().plusDays(1)); // or from DTO if needed
                    }

                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(applicant.getApplicantId());
                    notification.setAction("Interview Scheduled");
                    notification.setMessage("Your interview for job ID " + job.getId() + " has been scheduled.");
                    notification.setRoute("/applications");

                    try {
                        notificationService.sendNotification(notification);
                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                } else if (applicant.getApplicationStatus().equals(ApplicationStatus.OFFERED)) {
                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(applicant.getApplicantId());
                    notification.setAction("Job Offer");
                    notification.setMessage("Congratulations! You have been offered the job for ID " + job.getId() + ".");
                    notification.setRoute("/applications");

                    try {
                        notificationService.sendNotification(notification);
                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                }
                else if (applicant.getApplicationStatus().equals(ApplicationStatus.ACCEPTED)) {
                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(job.getPostedBy());
                    notification.setAction("Offer Accepted");
                    notification.setMessage("Applicant with ID " + applicant.getApplicantId() + " has accepted your job offer.");
                    notification.setRoute("/manage-jobs");

                    try {
                        notificationService.sendNotification(notification);
                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                }
                else if (applicant.getApplicationStatus().equals(ApplicationStatus.REJECTED)) {
                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(job.getPostedBy());
                    notification.setAction("Offer Rejected");
                    notification.setMessage("Applicant with ID " + applicant.getApplicantId() + " has rejected your job offer.");
                    notification.setRoute("/manage-jobs");

                    try {
                        notificationService.sendNotification(notification);
                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                }
                else if(applicant.getApplicationStatus().equals(ApplicationStatus.APPLIED)){
                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(job.getPostedBy());
                    notification.setAction("New Application Received");
                    notification.setMessage("A new applicant has applied for your job posting (Job ID: " + job.getId() + ").");
                    notification.setRoute("/manage-jobs");

                    try {
                        notificationService.sendNotification(notification);
                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                }
            }

            // Re-save the job with updated interview times
            jobRepository.save(job);
        }

        return job.toDTO();
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
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"));

        List<Applicant> applicants = job.getApplicants();
        if (applicants == null) {
            applicants = new ArrayList<>();
        }

        boolean alreadyApplied = applicants.stream()
                .anyMatch(x -> x.getApplicantId().equals(applicantDTO.getApplicantId()));

        if (alreadyApplied) {
            throw new JobSparkException("JOB_APPLIED_ALREADY");
        }

        applicantDTO.setApplicationStatus(ApplicationStatus.APPLIED);
        applicantDTO.setTimeStamp(LocalDateTime.now());
        applicants.add(applicantDTO.toEntity());
        job.setApplicants(applicants);
        jobRepository.save(job);

        return applicantDTO;
    }

    /**
     * Retrieves jobs posted by a specific poster.
     *
     * @param id Poster ID
     * @return List of JobDTOs
     * @throws JobSparkException if error occurs
     */
    @Override
    public List<JobDTO> getJobPosterById(Long id) throws JobSparkException {
        return jobRepository.findPosterById(id)
                .stream()
                .map(Job::toDTO)
                .toList();
    }

    /**
     * Updates the application status and/or interview time for a specific applicant.
     *
     * @param jobId Job ID
     * @param applicantId Applicant ID
     * @param updatedApplicant Updated applicant data (status/interview time)
     * @return Updated JobDTO
     * @throws JobSparkException if job or applicant not found
     */
    @Override
    public JobDTO updateApplicantStatus(Long jobId, Long applicantId, ApplicantDTO updatedApplicant) throws JobSparkException {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new JobSparkException("JOB_NOT_FOUND"));

        boolean found = false;

        for (Applicant applicant : job.getApplicants()) {
            if (applicant.getApplicantId().equals(applicantId)) {
                applicant.setApplicationStatus(updatedApplicant.getApplicationStatus());
                if (applicant.getApplicationStatus().equals(ApplicationStatus.INTERVIEWING)) {
                    applicant.setInterviewTime(updatedApplicant.getInterviewTime());

                    NotificationsDTO notification = new NotificationsDTO();
                    notification.setUserId(applicantId);
                    notification.setAction("Interview Scheduled");
                    notification.setMessage("Your interview for job ID " + jobId + " has been scheduled.");
                    notification.setRoute("/applications");

                    try {
                        notificationService.sendNotification(notification);

                    } catch (JobSparkException e) {
                        throw new JobSparkException("NOTIFICATION_FAILED: " + e.getMessage());
                    }
                }


                found = true;
                break;
            }
        }

        if (!found) {
            throw new JobSparkException("APPLICANT_NOT_FOUND");
        }

        Job updatedJob = jobRepository.save(job);
        return updatedJob.toDTO();
    }
}
