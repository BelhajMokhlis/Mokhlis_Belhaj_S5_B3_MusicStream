package com.example.MusicStream.mapper;

import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.model.Users;
import com.example.MusicStream.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(rolesToRoleNames(user.getRoles()))")
    public abstract UserResponse toResponse(Users user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "roles", ignore = true)
    public abstract Users toEntity(UserRequest request);

    default List<String> rolesToRoleNames(List<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toList());
    }  
}
