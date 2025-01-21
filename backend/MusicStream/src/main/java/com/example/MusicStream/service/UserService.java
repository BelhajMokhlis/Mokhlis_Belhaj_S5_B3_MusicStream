package com.example.MusicStream.service;

import com.example.MusicStream.dto.request.LoginRequest;
import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.request.UpdateRoleRequest;
import com.example.MusicStream.dto.response.LoginResponse;
import com.example.MusicStream.dto.response.UserResponse;
import java.util.List;

public interface UserService {
    LoginResponse login(LoginRequest request);
    UserResponse register(UserRequest request);
    List<UserResponse> getAllUsers();
    UserResponse updateUserRoles(String id, UpdateRoleRequest request);
} 