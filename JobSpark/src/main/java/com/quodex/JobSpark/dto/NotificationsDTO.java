package com.quodex.JobSpark.dto;

import com.quodex.JobSpark.dto.NotificationStatus;
import com.quodex.JobSpark.entity.Notifications;
import com.quodex.JobSpark.service.NotificationService;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

public class NotificationsDTO {
    private Long id;
    private Long userId;
    private String message;
    private String action;
    private String route;
    private LocalDateTime timeStamp;
    private NotificationStatus notificationStatus;
    public NotificationsDTO(Long id, Long userId, String message, String action, String route, LocalDateTime timeStamp, NotificationStatus notificationStatus) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.action = action;
        this.route = route;
        this.timeStamp = timeStamp;
        this.notificationStatus = notificationStatus;
    }

    public NotificationsDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Notifications toEntity(){
        return new Notifications(this.id, this.userId, this.message, this.action, this.route, this.timeStamp, this.notificationStatus);
    }
}

