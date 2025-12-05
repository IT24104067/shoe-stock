package com.example.shoestock.repo;

import com.example.shoestock.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByTargetUserId(String targetUserId);
}
