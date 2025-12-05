package com.example.shoestock.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    private String userId;
    private String shoeTypeId;
    private String text;
    private Instant createdAt;
    private String replyByUserId;
    private String replyText;
}
