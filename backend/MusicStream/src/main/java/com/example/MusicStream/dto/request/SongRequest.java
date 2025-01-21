package com.example.MusicStream.dto.request;

import jakarta.validation.constraints.NotBlank;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SongRequest {

    private String id;
    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    
    @NotNull(message = "La durée est obligatoire")
    @Min(value = 1, message = "La durée doit être supérieure à 0")
    private Integer duration;
    
    @NotNull(message = "Le numéro de piste est obligatoire")
    @Min(value = 1, message = "Le numéro de piste doit être supérieur à 0")
    private Integer trackNumber;

   
    
    @NotNull(message = "L'ID de l'album est obligatoire")
    private String albumId;
} 