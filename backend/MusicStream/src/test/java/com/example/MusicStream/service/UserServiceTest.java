package com.example.MusicStream.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.model.Role;
import com.example.MusicStream.model.Users;
import com.example.MusicStream.repository.RoleRepository;
import com.example.MusicStream.repository.UserRepository;
import com.example.MusicStream.service.impl.UserServiceImpl;
import com.example.MusicStream.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private Users user;
    private UserRequest userRequest;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId("1");
        role.setName("ROLE_USER");

        user = new Users();
        user.setId("1");
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setRoles(new ArrayList<>(List.of(role)));

        userRequest = new UserRequest();
        userRequest.setUsername("testUser");
        userRequest.setPassword("password");
        userRequest.setRoles(new ArrayList<>());
    }

    @Test
    void getAllUsers_ShouldReturnPageOfUsers() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        List<Users> users = List.of(user);
        Page<Users> userPage = new PageImpl<>(users, pageable, users.size());
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("testUser");
        
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toResponse(any(Users.class))).thenReturn(userResponse);

        // Act
        Page<UserResponse> result = userService.getAllUsers(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("testUser", result.getContent().get(0).getUsername());
    }

    @Test
    void registerUser_ShouldCreateNewUser() {
        // Arrange
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("testUser");
        
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.toEntity(any())).thenReturn(user);
        when(userMapper.toResponse(any(Users.class))).thenReturn(userResponse);

        // Act
        UserResponse result = userService.registerUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    void deleteUser_ShouldDeleteExistingUser() {
        // Arrange
        String userId = "1";

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void assignRoleToUser_ShouldAddRoleToUser() {
        // Arrange
        String userId = "1";
        String roleId = "1";
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername("testUser");
        
        // Create user without the role
        Users userWithoutRole = new Users();
        userWithoutRole.setId("1");
        userWithoutRole.setUsername("testUser");
        userWithoutRole.setRoles(new ArrayList<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(userWithoutRole));
        when(roleService.findById(roleId)).thenReturn(role);
        when(userRepository.save(any())).thenReturn(userWithoutRole);
        when(userMapper.toResponse(any(Users.class))).thenReturn(userResponse);

        // Act
        UserResponse result = userService.assignRoleToUser(userId, roleId);

        // Assert
        assertNotNull(result);
    }

    @Test
    void desassignRoleToUser_ShouldRemoveRoleFromUser() {
        // Arrange
        String userId = "1";
        String roleId = "1";
        user.setRoles(new ArrayList<>(List.of(role))); // Ensure user has the role

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.findById(roleId)).thenReturn(role);
        when(userRepository.save(any())).thenReturn(user);

        // Act
        userService.desassignRoleToUser(userId, roleId);

        // Assert
        verify(userRepository, times(1)).save(any());
    }
    }

