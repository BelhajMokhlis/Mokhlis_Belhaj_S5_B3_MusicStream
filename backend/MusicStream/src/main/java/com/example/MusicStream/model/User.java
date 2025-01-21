package com.example.MusicStream.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

import org.springframework.data.mongodb.core.index.Indexed;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String login;

    private String password;

    private Boolean active = true;

    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
        }
        this.roles.add(role);
    }
} 