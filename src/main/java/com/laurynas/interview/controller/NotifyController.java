package com.laurynas.interview.controller;

import org.springframework.web.bind.annotation.RestController;

import com.laurynas.interview.model.Notification;
import com.laurynas.interview.service.NotificationService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/notify")
public class NotifyController {
	
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotifications() {
        return notificationService.getAllNotifications();
    }

    // Placeholder methods for creating notifications
    // In a real application, these would be an external service
    // that accepts POST requests with specific data.
    @GetMapping("/type1")
    public ResponseEntity<?> gotNotificationType1() {
        return ResponseEntity.ok("Notification for TYPE1 received successfully.");
    }

    @GetMapping("/type2")
    public ResponseEntity<?> createNotificationType2() {
        return ResponseEntity.ok("Notification for TYPE2 received successfully.");
    }
}