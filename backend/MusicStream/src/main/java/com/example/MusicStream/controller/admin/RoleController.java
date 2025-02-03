package com.example.MusicStream.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MusicStream.dto.request.RoleRequest;
import com.example.MusicStream.dto.response.RoleResponse;
import com.example.MusicStream.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Role Controller", description = "Gestion des rôles par l'administrateur")
@RequestMapping("/api/admin/roles")
public class RoleController {
    
    @Autowired
    private RoleService roleService;

    /***
     * Get all roles    
     * @return
     */
    @Operation(summary = "Get all roles", description = "Récupérer tous les rôles")
    @ApiResponse(responseCode = "200", description = "Rôles récupérés avec succès")
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    /***
     * Create a new role
     * @param request The role creation request
     * @return The created role response
     */
    @Operation(summary = "Create a new role", description = "Créer un nouveau rôle")
    @ApiResponse(responseCode = "201", description = "Rôle créé avec succès")
    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide - Erreurs de validation")
    @ApiResponse(responseCode = "409", description = "Rôle déjà existant")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request));
    }
}
