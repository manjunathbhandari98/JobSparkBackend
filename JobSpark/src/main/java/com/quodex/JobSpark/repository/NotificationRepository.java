package com.quodex.JobSpark.repository;

import com.quodex.JobSpark.dto.NotificationStatus;
import com.quodex.JobSpark.entity.Notifications;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notifications, Long> {
    public List<Notifications> findByUserIdAndNotificationStatus(Long userId, NotificationStatus status);
}
