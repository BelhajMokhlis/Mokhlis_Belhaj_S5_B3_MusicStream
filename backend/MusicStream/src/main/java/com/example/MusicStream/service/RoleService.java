package com.example.MusicStream.service;

import java.util.List;

import com.example.MusicStream.dto.request.RoleRequest;
import com.example.MusicStream.dto.response.RoleResponse;
import com.example.MusicStream.model.Role;

public interface RoleService {
    
    public List<RoleResponse> getAllRoles();
    public RoleResponse createRole(RoleRequest request);
    public Role findById(String id);
    public Role findByName(String name);
}
