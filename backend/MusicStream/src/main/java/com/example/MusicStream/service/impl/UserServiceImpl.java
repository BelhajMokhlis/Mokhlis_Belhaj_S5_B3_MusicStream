package com.example.MusicStream.service.impl;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.MusicStream.service.UserService;
import com.example.MusicStream.repository.UserRepository;
import com.example.MusicStream.service.RoleService;
import com.example.MusicStream.mapper.UserMapper;
import com.example.MusicStream.model.Users;
import com.example.MusicStream.model.Role;
import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.Exception.ResponseException;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserMapper userMapper;  

    @Autowired
    private PasswordEncoder passwordEncoder;

  

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
    
    	
        Users user = userMapper.toEntity(request);
        
        Role role = roleService.findByName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        user = userRepository.save(user);
        logger.info("User registered successfully: {}", user.getUsername());
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse assignRoleToUser(String userId, String roleId) {
        Optional<Users> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseException("User not found ", HttpStatus.NOT_FOUND);
        }
        Role role = roleService.findById(roleId);
        
        if (user.get().getRoles().contains(role)) {
            throw new ResponseException("User already has role", HttpStatus.BAD_REQUEST);
        }
        user.get().getRoles().add(role);
        return userMapper.toResponse(userRepository.save(user.get()));
    }

    @Override
    public void desassignRoleToUser(String userId, String roleId) {
        Optional<Users> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new ResponseException("User not found", HttpStatus.NOT_FOUND);
        }
        Role role = roleService.findById(roleId);
       
        // cheque si user has role
        if (!user.get().getRoles().contains(role)) {
            throw new ResponseException("User does not have role", HttpStatus.BAD_REQUEST);
        }
        user.get().getRoles().remove(role);
        userRepository.save(user.get());

    }

  
}
