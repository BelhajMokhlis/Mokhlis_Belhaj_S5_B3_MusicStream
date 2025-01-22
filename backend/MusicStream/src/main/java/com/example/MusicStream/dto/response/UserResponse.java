package com.example.MusicStream.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private List<String> roles;
}
