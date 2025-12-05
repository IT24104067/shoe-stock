package com.example.shoestock.repo;

import com.example.shoestock.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByShoeTypeId(String shoeTypeId);
}
