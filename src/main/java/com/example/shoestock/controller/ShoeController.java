package com.example.shoestock.controller;

import com.example.shoestock.model.*;
import com.example.shoestock.service.ShoeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ShoeController {
    private final ShoeService shoeService;

    public ShoeController(ShoeService shoeService) { this.shoeService = shoeService; }

    @PreAuthorize("hasAnyAuthority('ROLE_ASSISTANT','ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ShoeType s) {
        return ResponseEntity.ok(shoeService.createShoe(s));
    }

    @GetMapping
    public ResponseEntity<List<ShoeType>> list() {
        return ResponseEntity.ok(shoeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return ResponseEntity.ok(shoeService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ASSISTANT','ROLE_MANAGER')")
    @PostMapping("/{id}/size")
    public ResponseEntity<?> addSize(@PathVariable String id, @RequestBody ShoeSizeEntry entry) {
        return ResponseEntity.ok(shoeService.addSize(id, entry));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ASSISTANT','ROLE_MANAGER')")
    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(@PathVariable String id, @RequestParam MultipartFile file) throws IOException {
        return ResponseEntity.ok(shoeService.addImage(id, file));
    }
}
