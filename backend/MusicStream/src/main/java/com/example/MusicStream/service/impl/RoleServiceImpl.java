package com.example.MusicStream.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.MusicStream.service.RoleService;
import com.example.MusicStream.repository.RoleRepository;
import com.example.MusicStream.mapper.RoleMapper;
import com.example.MusicStream.model.Role;
import com.example.MusicStream.dto.request.RoleRequest;
import com.example.MusicStream.dto.response.RoleResponse;
import com.example.MusicStream.Exception.ResponseException;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        // first add prifix ROLE_ to the name
        request.setName("ROLE_" + request.getName());
        // check if the role already exists
        if(roleRepository.findByName(request.getName()).isPresent()){
            throw new ResponseException("Role already exists", HttpStatus.CONFLICT);
        }
        Role role = roleMapper.toEntity(request);
        role = roleRepository.save(role);
        return roleMapper.toResponse(role);
    }

    @Override
    public Role findById(String id) {
        Optional<Role> role = roleRepository.findById(id);
        if (!role.isPresent()) {
            throw new ResponseException("Role not found 1 ", HttpStatus.NOT_FOUND);
        }
        return role.get();
    }

    public Role findByName(String name) {
        Optional<Role> role = roleRepository.findByName(name);
        if(role.isPresent()){
            return role.get();
        }
        throw new ResponseException("Role not found", HttpStatus.NOT_FOUND);
    }
}
