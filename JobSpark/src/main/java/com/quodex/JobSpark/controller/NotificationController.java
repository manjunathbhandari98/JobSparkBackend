package com.quodex.JobSpark.controller;

import com.quodex.JobSpark.dto.NotificationStatus;
import com.quodex.JobSpark.entity.Notifications;
import com.quodex.JobSpark.exception.JobSparkException;
import com.quodex.JobSpark.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/notifications")

public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Notifications>> getNotifications(@PathVariable Long userId){
        return new ResponseEntity<>(notificationService.getUnreadNotifications(userId), HttpStatus.OK) ;
    }

    @PutMapping("/update/{userId}/{notificationId}")
    public ResponseEntity<Notifications> updateStatus(
            @PathVariable Long userId,
            @PathVariable Long notificationId,
            @RequestBody NotificationStatus status) throws JobSparkException {
        Notifications updated = notificationService.updateStatus(userId, notificationId, status);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
