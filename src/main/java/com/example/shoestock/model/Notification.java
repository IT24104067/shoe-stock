package com.example.shoestock.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String targetUserId; // null => broadcast
    private String message;
    private boolean read = false;
    private Instant createdAt;
}
