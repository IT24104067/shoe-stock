package com.example.shoestock.controller;

import com.example.shoestock.model.Comment;
import com.example.shoestock.repo.CommentRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository commentRepo;
    public CommentController(CommentRepository cr) { this.commentRepo = cr; }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody Comment c) {
        c.setCreatedAt(Instant.now());
        return ResponseEntity.ok(commentRepo.save(c));
    }

    @GetMapping("/shoe/{shoeId}")
    public ResponseEntity<List<Comment>> forShoe(@PathVariable String shoeId) {
        return ResponseEntity.ok(commentRepo.findByShoeTypeId(shoeId));
    }

    // Salesman reply
    @PreAuthorize("hasAuthority('ROLE_SALESMAN')")
    @PostMapping("/{id}/reply")
    public ResponseEntity<?> reply(@PathVariable String id, @RequestParam String replyText, @RequestParam String replyByUserId) {
        var c = commentRepo.findById(id).orElseThrow();
        c.setReplyText(replyText);
        c.setReplyByUserId(replyByUserId);
        return ResponseEntity.ok(commentRepo.save(c));
    }
}
