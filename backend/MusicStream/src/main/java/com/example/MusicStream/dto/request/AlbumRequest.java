package com.example.MusicStream.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumRequest {

    private String id;
    
    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    
    @NotBlank(message = "L'artiste est obligatoire")
    private String artist;
    
    @Min(value = 1980, message = "L'année de sortie doit être supérieure à 1980")
    @Max(value = 2024, message = "L'année de sortie doit être inférieure à 2024")
    private Integer releaseYear;
    
    @NotBlank(message = "Le genre est obligatoire")
    private String genre;
    
} 