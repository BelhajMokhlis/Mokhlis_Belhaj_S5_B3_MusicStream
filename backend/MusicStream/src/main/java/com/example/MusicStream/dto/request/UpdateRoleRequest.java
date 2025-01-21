package com.example.MusicStream.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.MusicStream.model.Role;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequest {
    @NotEmpty(message = "Roles cannot be empty")
    private Set<Role> roles;
} 