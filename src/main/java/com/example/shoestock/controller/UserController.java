package com.example.shoestock.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoestock.model.Role;
import com.example.shoestock.model.User;
import com.example.shoestock.repo.UserRepository;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // List users (IT and Manager)
    @PreAuthorize("hasAnyAuthority('ROLE_IT','ROLE_MANAGER')")
    @GetMapping
    public List<User> list() {
        return userRepository.findAll();
    }

    // Create user (IT only)
    public record CreateUserReq(String username, String password, String displayName, Set<Role> roles) {}

    @PreAuthorize("hasAuthority('ROLE_IT')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserReq req) {
        if (userRepository.existsByUsername(req.username())) {
            return ResponseEntity.badRequest().body("exists");
        }
        User u = new User();
        u.setUsername(req.username());
        u.setPasswordHash(encoder.encode(req.password()));
        u.setDisplayName(req.displayName());
        u.setRoles(req.roles());
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }

    // Update user (IT only)
    public record UpdateUserReq(String username, Set<Role> roles) {}

    @PreAuthorize("hasAuthority('ROLE_IT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody UpdateUserReq req) {
        User u = userRepository.findById(id).orElseThrow();
        if (req.username() != null && !req.username().isBlank()) {
            u.setUsername(req.username());
        }
        if (req.roles() != null && !req.roles().isEmpty()) {
            u.setRoles(req.roles());
        }
        userRepository.save(u);
        return ResponseEntity.ok(u);
    }

    // Delete user (IT only)
    @PreAuthorize("hasAuthority('ROLE_IT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("ok");
    }
}
