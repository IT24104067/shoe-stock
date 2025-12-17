package com.example.shoestock.util;

import com.example.shoestock.model.Role;
import com.example.shoestock.model.User;
import com.example.shoestock.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner init(UserRepository userRepo, BCryptPasswordEncoder encoder) {
        return args -> {
            seedUserIfMissing(userRepo, encoder, "itadmin", "ChangeMe123!", "IT Admin", Set.of(Role.ROLE_IT));
            seedUserIfMissing(userRepo, encoder, "manager1", "Manager@123", "Store Manager", Set.of(Role.ROLE_MANAGER));
            seedUserIfMissing(userRepo, encoder, "salesman1", "Sales@123", "Sales Representative", Set.of(Role.ROLE_SALESMAN));
            seedUserIfMissing(userRepo, encoder, "assistant1", "Assist@123", "Store Assistant", Set.of(Role.ROLE_ASSISTANT));
            seedUserIfMissing(userRepo, encoder, "user1", "User@123", "Customer User", Set.of(Role.ROLE_USER));
        };
    }

    private void seedUserIfMissing(UserRepository userRepo, BCryptPasswordEncoder encoder,
                                   String username, String rawPassword, String displayName, Set<Role> roles) {
        var existing = userRepo.findByUsername(username);
        if (existing.isPresent()) {
            // If roles are missing (e.g., user was created with default USER role), ensure correct role set.
            User u = existing.get();
            if (u.getRoles() == null || !u.getRoles().equals(roles)) {
                u.setRoles(roles);
                userRepo.save(u);
                System.out.println("Updated roles for " + username + " -> " + roles);
            }
            return; // Do not override passwords for existing users
        }

        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(rawPassword));
        u.setDisplayName(displayName);
        u.setRoles(roles);
        userRepo.save(u);
        System.out.println("Created default user: " + username + " / " + rawPassword);
    }
}
