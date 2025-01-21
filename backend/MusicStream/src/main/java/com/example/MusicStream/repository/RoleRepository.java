package com.example.MusicStream.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.MusicStream.model.Role;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
    Optional<Role> findById(String id);
}