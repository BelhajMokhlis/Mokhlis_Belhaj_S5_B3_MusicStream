package com.example.MusicStream.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.MusicStream.model.Role;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String login;
    private Boolean active;
    private Set<Role> roles;
} 