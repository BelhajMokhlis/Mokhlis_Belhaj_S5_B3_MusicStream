package com.example.MusicStream.service.impl;

import com.example.MusicStream.dto.request.LoginRequest;
import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.request.UpdateRoleRequest;
import com.example.MusicStream.dto.response.LoginResponse;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.mapper.UserMapper;
import com.example.MusicStream.model.Role;
import com.example.MusicStream.model.User;
import com.example.MusicStream.repository.UserRepository;
import com.example.MusicStream.service.UserService;
import com.example.MusicStream.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );
        
        User user = userRepository.findByLogin(request.getLogin())
            .orElseThrow(() -> new RuntimeException("User not found"));
            
        String token = jwtTokenProvider.generateToken(authentication);
        return new LoginResponse(token, userMapper.toResponse(user));
    }

    @Override
    public UserResponse register(UserRequest request) {
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new RuntimeException("Login already exists");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(new HashSet<>());
        user.addRole(Role.ROLE_USER);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseList(users);
    }

    @Override
    public UserResponse updateUserRoles(String id, UpdateRoleRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        userMapper.updateRoles(user, request);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }
} 