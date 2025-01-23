
package com.example.MusicStream.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.MusicStream.dto.request.ChansonRequest;
import com.example.MusicStream.dto.response.ChansonResponse;
import com.example.MusicStream.service.ChansonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/chansons")
@Tag(name = "Admin chansons Controller", description = "Gestion des chansons par l'administrateur")
public class AdminChansonontroller {

    @Autowired
    private ChansonService chansonService;

    /**
     * Create a new chansons
     * @param request The chansons creation request
     * @return The created chansons response
     */
    @Operation(
        summary = "Créer une nouvelle chanson",
        description = "Crée une nouvelle chanson avec les informations fournies"
    )
    @ApiResponse(responseCode = "201", description = "Chanson créée avec succès")
    @ApiResponse(responseCode = "400", description = "Corps de la requête invalide - Erreurs de validation" ,
     content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "Validation failed: title: Title is required",
            "status": 400
        }
        """)))
    @PostMapping
    public ResponseEntity<ChansonResponse> createchansons( @Valid @RequestBody ChansonRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(chansonService.createChanson(request));
    }


    /***
     * Update a chansons
     * @param id The chansons ID
     * @param request The chansons update request
     * @return The updated chansons response
     */
    @Operation(
        summary = "Mettre à jour une chanson",
        description = "Met à jour une chanson avec les informations fournies"
    )
    @ApiResponse(responseCode = "200", description = "Chanson mise à jour avec succès")
    @ApiResponse(responseCode = "404", description = "Chanson non trouvée")
    @PutMapping("/{id}")
    public ResponseEntity<ChansonResponse> updatechansons(@PathVariable String id,  @RequestBody ChansonRequest request) {
        return ResponseEntity.ok(chansonService.updateChanson(id, request));
    }

    /***
     * Delete a chansons
     * @param id The chansons ID
     * @return The deleted chansons response
     */
    @Operation(
        summary = "Supprimer une chanson",
        description = "Supprime une chanson avec l'ID fourni"
    )
    @ApiResponse(responseCode = "200", description = "Chanson supprimée avec succès")
    @ApiResponse(responseCode = "404", description = "Chanson non trouvée")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletechansons(@PathVariable String id) {
        chansonService.deleteChanson(id);
        return ResponseEntity.noContent().build();
    }

    
}
