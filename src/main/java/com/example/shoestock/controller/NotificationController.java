package com.example.shoestock.controller;

import com.example.shoestock.model.Notification;
import com.example.shoestock.repo.NotificationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationRepository repo;
    public NotificationController(NotificationRepository repo) { this.repo = repo; }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> forUser(@PathVariable String userId) {
        return ResponseEntity.ok(repo.findByTargetUserId(userId));
    }
}
