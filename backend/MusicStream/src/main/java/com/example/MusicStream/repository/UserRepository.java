package com.example.MusicStream.repository;

import com.example.MusicStream.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<Users, String> {
    Optional<Users> findByUsername(String username);
} 