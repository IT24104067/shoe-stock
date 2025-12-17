package com.example.shoestock.controller;

import com.example.shoestock.model.*;
import com.example.shoestock.repo.UserRepository;
import com.example.shoestock.security.JwtUtil;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserRepository userRepo, BCryptPasswordEncoder encoder) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    record LoginReq(String username, String password) {}
    record LoginResp(String token) {}
    record CreateStaffReq(String username, String password, String displayName, Set<Role> roles) {}
    record RegisterReq(String username, String password, String displayName) {}
    record ChangePwdReq(String newPassword) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq req) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        var user = userRepo.findByUsername(req.username()).orElseThrow();
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toSet());
        String token = jwtUtil.generateToken(user.getUsername(), roles);
        return ResponseEntity.ok(new LoginResp(token));
    }

    // Create staff - only IT role (enforce via @PreAuthorize in future or call by IT token)
    @PreAuthorize("hasAnyAuthority('ROLE_IT','ROLE_MANAGER')")
    @PostMapping("/create-staff")
    public ResponseEntity<?> createStaff(@RequestBody CreateStaffReq r) {
        if (userRepo.existsByUsername(r.username())) return ResponseEntity.badRequest().body("exists");
        User u = new User();
        u.setUsername(r.username());
        u.setPasswordHash(encoder.encode(r.password()));
        u.setDisplayName(r.displayName());
        u.setRoles(r.roles());
        userRepo.save(u);
        return ResponseEntity.ok(u);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq r) {
        if (userRepo.existsByUsername(r.username())) return ResponseEntity.badRequest().body("exists");
        User u = new User();
        u.setUsername(r.username());
        u.setPasswordHash(encoder.encode(r.password()));
        u.setDisplayName(r.displayName());
        u.setRoles(Set.of(Role.ROLE_USER));
        userRepo.save(u);
        return ResponseEntity.ok(u);
    }

    // password change - requires authentication (use SecurityContext in requests)
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePwdReq r, Authentication authentication) {
        String username = authentication.getName();
        User u = userRepo.findByUsername(username).orElseThrow();
        u.setPasswordHash(encoder.encode(r.newPassword()));
        userRepo.save(u);
        return ResponseEntity.ok("ok");
    }
}
