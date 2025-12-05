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
            if (!userRepo.existsByUsername("itadmin")) {
                User u = new User();
                u.setUsername("itadmin");
                u.setPasswordHash(encoder.encode("ChangeMe123!"));
                u.setDisplayName("IT Admin");
                u.setRoles(Set.of(Role.ROLE_IT));
                userRepo.save(u);
                System.out.println("Created default IT admin: itadmin / ChangeMe123!");
            }
        };
    }
}
