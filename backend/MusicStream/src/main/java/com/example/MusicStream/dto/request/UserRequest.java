package com.example.MusicStream.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
    private List<RoleRequest> roles;
}


