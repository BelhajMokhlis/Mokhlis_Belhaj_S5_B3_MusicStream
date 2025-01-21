package com.example.MusicStream.mapper;

import com.example.MusicStream.dto.request.UserRequest;
import com.example.MusicStream.dto.request.UpdateRoleRequest;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.List;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserResponse toResponse(User user);
    
    // This will automatically use toResponse() for each User in the list
    List<UserResponse> toResponseList(List<User> users);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "active", constant = "true")
    User toEntity(UserRequest request);

    @Mapping(target = "password", ignore = true)
    void updateEntity(@MappingTarget User user, UserRequest request);
    
    @Mapping(target = "id", ignore = true)
    void updateRoles(@MappingTarget User user, UpdateRoleRequest request);
}
