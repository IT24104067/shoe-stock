package com.example.shoestock.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.shoestock.model.ShoeSizeEntry;
import com.example.shoestock.model.ShoeType;
import com.example.shoestock.service.ShoeService;

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

    @PreAuthorize("hasAnyAuthority('ROLE_ASSISTANT','ROLE_MANAGER','ROLE_IT')")
    @GetMapping("/low-stock")
    public ResponseEntity<List<ShoeType>> lowStock() {
        return ResponseEntity.ok(shoeService.findLowStockShoes());
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
