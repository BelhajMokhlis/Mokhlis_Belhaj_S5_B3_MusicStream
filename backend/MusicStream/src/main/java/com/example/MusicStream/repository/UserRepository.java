package com.example.MusicStream.repository;

import com.example.MusicStream.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
} 