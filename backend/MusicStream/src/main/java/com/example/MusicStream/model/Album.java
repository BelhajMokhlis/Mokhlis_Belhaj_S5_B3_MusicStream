package com.example.MusicStream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "albums")
public class Album {
    
    @Id
    private String id;
    
    private String title;
    private String artist;
    private Integer releaseYear;
    private String genre;
    @DBRef
    private List<Chanson> chansons = new ArrayList<>();  // List of Song IDs
} 