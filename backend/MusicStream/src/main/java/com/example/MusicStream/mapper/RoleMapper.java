package com.example.MusicStream.mapper;

import org.mapstruct.Mapper;

import com.example.MusicStream.dto.request.RoleRequest;
import com.example.MusicStream.dto.response.RoleResponse;
import com.example.MusicStream.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    public RoleResponse toResponse(Role role);

    public Role toEntity(RoleRequest request);



}
