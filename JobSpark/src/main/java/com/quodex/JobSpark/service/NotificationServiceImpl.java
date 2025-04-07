package com.quodex.JobSpark.service;

import com.quodex.JobSpark.dto.NotificationStatus;
import com.quodex.JobSpark.dto.NotificationsDTO;
import com.quodex.JobSpark.entity.Notifications;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.repository.NotificationRepository;
import com.quodex.JobSpark.utility.Utilites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(NotificationsDTO notificationsDTO) throws JobSparkException {
        notificationsDTO.setId(Utilites.getNextSequence("notifications"));
        notificationsDTO.setTimeStamp(LocalDateTime.now());
        notificationsDTO.setNotificationStatus(NotificationStatus.UNREAD);
        notificationRepository.save(notificationsDTO.toEntity());
    }

    @Override
    public List<Notifications> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndNotificationStatus(userId, NotificationStatus.UNREAD);
    }

    @Override
    public Notifications updateStatus(Long userId, Long notificationId, NotificationStatus status) throws JobSparkException {
        // Fetch notification by ID
        Notifications notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new JobSparkException("Notification not found with ID: " + notificationId));

        // Check if this notification belongs to the user
        if (!notification.getUserId().equals(userId)) {
            throw new JobSparkException("Notification does not belong to the given user.");
        }

        // Update status
        notification.setNotificationStatus(status);
        return notificationRepository.save(notification);
    }

}
