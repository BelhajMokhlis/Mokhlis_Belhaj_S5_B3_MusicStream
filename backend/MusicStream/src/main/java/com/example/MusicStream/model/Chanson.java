package com.example.MusicStream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chansons")
public class Chanson {
    
    @Id
    private String id;
    
    private String titre;
    private Integer duree;
    private Integer trackNumber;
    
    @Size(max = 200)
    private String description;
    
    private String categorie;
    private Date dateAjout = new Date();
    private String audioFileId;  // GridFS file reference
    
    @Field("album_id")
    private String albumId;  // Reference to the parent album
} 