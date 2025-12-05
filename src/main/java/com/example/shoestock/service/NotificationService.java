package com.example.shoestock.service;

import com.example.shoestock.model.Notification;
import com.example.shoestock.model.Role;
import com.example.shoestock.repo.NotificationRepository;
import com.example.shoestock.repo.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepo;
    private final UserRepository userRepo;

    public NotificationService(NotificationRepository notificationRepo, UserRepository userRepo) {
        this.notificationRepo = notificationRepo;
        this.userRepo = userRepo;
    }

    public void createNotification(String userId, String message) {
        Notification n = new Notification();
        n.setTargetUserId(userId);
        n.setMessage(message);
        n.setCreatedAt(Instant.now());
        notificationRepo.save(n);
    }

    public void createNotificationForRole(String message, Role role) {
        userRepo.findAll().stream()
                .filter(u -> u.getRoles() != null && u.getRoles().contains(role))
                .forEach(u -> createNotification(u.getId(), message));
    }
}
