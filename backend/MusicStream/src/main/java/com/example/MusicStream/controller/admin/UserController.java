package com.example.MusicStream.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MusicStream.Exception.ResponseException;
import com.example.MusicStream.dto.response.UserResponse;
import com.example.MusicStream.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Users", description = "API pour la gestion des utilisateurs")
public class UserController {

    @Autowired
    private UserService userService;

    /***
     * Get all users
     * @param page The page number
     * @param size The page size
     * @param sortBy The field to sort by
     * @param sortOrder The sort order
     * @return The list of users
     */
    @Operation(summary = "Récupérer tous les utilisateurs", description = "Récupérer tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Utilisateurs récupérés avec succès")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    /***
     * Assign a role to a user
     * @param userId The user ID
     * @param roleId The role ID
     * @return The user response
     */
    @Operation(summary = "Assigner un rôle à un utilisateur", description = "Assigner un rôle à un utilisateur")
    @ApiResponse(responseCode = "200", description = "Rôle assigné avec succès")
    @PutMapping("/roles/{userId}")
    public ResponseEntity<UserResponse> assignRoleToUser(@PathVariable String userId,@Valid @RequestParam String roleId) {
        if (userId == null || roleId == null) {
            throw new ResponseException("User ID and Role ID are required", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(userService.assignRoleToUser(userId, roleId));
    }

    /***
     * Desassign a role to a user
     * @param userId The user ID
     * @param roleId The role ID
     * @return The user response
     */
    @Operation(summary = "Désassigner un rôle à un utilisateur", description = "Désassigner un rôle à un utilisateur")
    @ApiResponse(responseCode = "204", description = "Rôle désassigné avec succès")
    @DeleteMapping("/roles/{userId}")
    public ResponseEntity<Void> desassignRoleToUser(@PathVariable String userId,@Valid @RequestParam String roleId) {
        userService.desassignRoleToUser(userId, roleId);
        return ResponseEntity.noContent().build();
    }

    /***
     * Delete a user
     * @param userId The user ID
     * @return The user response
     */
    @Operation(summary = "Supprimer un utilisateur", description = "Supprimer un utilisateur")
    @ApiResponse(responseCode = "204", description = "Utilisateur supprimé avec succès")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
