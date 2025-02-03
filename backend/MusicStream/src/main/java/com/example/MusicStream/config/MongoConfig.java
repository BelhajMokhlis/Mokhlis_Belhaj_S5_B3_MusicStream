package com.example.MusicStream.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.MongoDatabaseFactory;


import jakarta.annotation.PostConstruct;

import com.example.MusicStream.model.Users;

@Configuration
public class MongoConfig  {

    private static final Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    @Autowired
    private MongoTemplate mongoTemplate;

   
    @PostConstruct
    public void initIndexes() {
        if (mongoTemplate == null) {
            logger.error("MongoTemplate is not initialized. Index creation skipped.");
            return;
        }

        try {
            IndexOperations indexOps = mongoTemplate.indexOps(Users.class);

            // Check if index exists before dropping
            if (indexOps.getIndexInfo().stream()
                .anyMatch(index -> "username_1".equals(index.getName()))) {
                indexOps.dropIndex("username_1");
                logger.info("Dropped existing index: username_1");
            }

            // Create unique index for username
            IndexDefinition usernameIndex = new Index()
                .on("username", Sort.Direction.ASC)
                .unique();
            indexOps.ensureIndex(usernameIndex);
            logger.info("Created unique index on 'username'");
        } catch (Exception e) {
            logger.error("Error creating index: {}", e.getMessage(), e);
        }
    }

  



 
}
