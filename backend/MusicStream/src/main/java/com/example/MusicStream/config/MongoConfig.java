package com.example.MusicStream.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;
import jakarta.annotation.PostConstruct;

import com.example.MusicStream.model.Users;

@Configuration
public class MongoConfig {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void initIndexes() {
        try {
            IndexOperations indexOps = mongoTemplate.indexOps(Users.class);
            // Drop existing indexes to avoid conflicts
            indexOps.dropIndex("username_1");
            
            IndexDefinition usernameIndex = new Index().on("username", org.springframework.data.domain.Sort.Direction.ASC).unique();
            indexOps.ensureIndex(usernameIndex);
        } catch (Exception e) {
            // Log error but don't fail startup
            System.err.println("Error creating index: " + e.getMessage());
        }
    }
}