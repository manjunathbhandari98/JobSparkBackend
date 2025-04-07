package com.quodex.JobSpark.controller;

import com.quodex.JobSpark.dto.ApplicantDTO;
import com.quodex.JobSpark.dto.JobDTO;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping()
    public ResponseEntity<List<JobDTO>> getAllJobs() throws JobSparkException{
        return new ResponseEntity<> (jobService.getAllJobs(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable Long id) throws JobSparkException{
        return new ResponseEntity<JobDTO>(jobService.getJobById(id), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<JobDTO> postJob(@RequestBody JobDTO jobDTO) throws JobSparkException{
        return new ResponseEntity<JobDTO>(jobService.postJob(jobDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteJobById(@PathVariable Long id) throws JobSparkException{
        jobService.deleteJobById(id);
        return "Job Deleted Successfully";
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) throws JobSparkException{
        return new ResponseEntity<JobDTO>(jobService.updateJob(id, jobDTO), HttpStatus.OK);
    }

    @PostMapping("/apply/{id}")
    public ResponseEntity<ApplicantDTO> applyJob(@PathVariable Long id, @RequestBody ApplicantDTO applicantDTO) throws JobSparkException{
        return new ResponseEntity<ApplicantDTO>(jobService.applyJob(id, applicantDTO), HttpStatus.OK);
    }

    @GetMapping("/poster/{jobId}")
    public ResponseEntity<List<JobDTO>> getJobPoster(@PathVariable Long id) throws JobSparkException {
        return new ResponseEntity<>(jobService.getJobPosterById(id), HttpStatus.OK);
    }

    @PutMapping("/{jobId}/applicants/{applicantId}")
    public ResponseEntity<JobDTO> updateApplicantStatus(
            @PathVariable Long jobId,
            @PathVariable Long applicantId,
            @RequestBody ApplicantDTO updatedApplicant
    ) throws JobSparkException {
        JobDTO updatedJob = jobService.updateApplicantStatus(jobId, applicantId, updatedApplicant);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }


}
