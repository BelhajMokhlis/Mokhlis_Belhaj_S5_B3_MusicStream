package com.example.MusicStream.controller;

import com.example.MusicStream.dto.request.LoginRequest;
import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.request.UpdateRoleRequest;
import com.example.MusicStream.dto.response.ApiResponse;
import com.example.MusicStream.dto.response.LoginResponse;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	@Autowired
    private  UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PutMapping("/admin/users/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserRoles(
            @PathVariable String id,
            @Valid @RequestBody UpdateRoleRequest request) {
        UserResponse response = userService.updateUserRoles(id, request);
        return ResponseEntity.ok(ApiResponse.success("User roles updated successfully", response));
    }
} 