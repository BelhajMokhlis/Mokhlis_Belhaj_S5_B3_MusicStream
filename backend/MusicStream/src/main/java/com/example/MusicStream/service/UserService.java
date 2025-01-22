package com.example.MusicStream.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.response.UserResponse;

public interface UserService {
    
    public Page<UserResponse> getAllUsers(Pageable pageable);
    public UserResponse registerUser(UserRequest request);
    public void deleteUser(String id);
    public UserResponse assignRoleToUser(String userId, String roleId);
    public void desassignRoleToUser(String userId, String roleId);
}
