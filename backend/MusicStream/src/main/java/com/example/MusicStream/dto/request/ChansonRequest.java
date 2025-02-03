package com.example.MusicStream.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.Duration;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChansonRequest {

    private String id;
    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    

    private Duration duration;
    
    @NotNull(message = "Le numéro de piste est obligatoire")
    @Min(value = 1, message = "Le numéro de piste doit être supérieur à 0")
    private Integer trackNumber;


    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotBlank(message = "La catégorie est obligatoire")
    private String categorie;

    private String audioFileId;

   
    
    @NotNull(message = "L'ID de l'album est obligatoire")
    private String albumId;
} 