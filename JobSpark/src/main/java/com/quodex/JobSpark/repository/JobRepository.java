package com.quodex.JobSpark.repository;

import com.quodex.JobSpark.entity.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JobRepository extends MongoRepository<Job, Long> {
    List<Job> findPosterById(Long postedBy);
}
