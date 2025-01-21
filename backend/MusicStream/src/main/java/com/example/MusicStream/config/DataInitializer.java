package com.example.MusicStream.config;

import com.example.MusicStream.model.Role;
import com.example.MusicStream.model.User;
import com.example.MusicStream.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Value("${admin.username:admin}")
    private String adminUsername;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByLogin(adminUsername)) {
            User admin = new User();
            admin.setLogin(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setActive(true);
            admin.setRoles(new HashSet<>());
            admin.addRole(Role.ROLE_ADMIN);
            userRepository.save(admin);
        }
    }
} 