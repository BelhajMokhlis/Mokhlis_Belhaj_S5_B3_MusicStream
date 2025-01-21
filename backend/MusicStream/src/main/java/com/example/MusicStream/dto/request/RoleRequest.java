package com.example.MusicStream.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoleRequest {
    private String id;
    @NotBlank(message = "Le nom du rôle est obligatoire")
    private String name;
}

