package com.quodex.JobSpark.repository;

import com.quodex.JobSpark.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
