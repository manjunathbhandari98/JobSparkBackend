package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.NotificationStatus;
import com.quodex.JobSpark.dto.NotificationsDTO;
import com.quodex.JobSpark.entity.Notifications;
import com.quodex.JobSpark.exception.JobSparkException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NotificationService {
    public void sendNotification(NotificationsDTO notificationsDTO) throws JobSparkException;
    public List<Notifications> getUnreadNotifications(Long userId);


    Notifications updateStatus(Long userId, Long notificationId, NotificationStatus status) throws JobSparkException;
}
