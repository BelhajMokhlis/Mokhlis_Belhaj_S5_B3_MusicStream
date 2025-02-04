package com.example.MusicStream.controller.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.MusicStream.dto.response.ChansonResponse;
import com.example.MusicStream.service.ChansonService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Client Chanson Controller", description = "Gestion des chansons par le client")
@RequestMapping("/api/users/chanson")
public class ClientChansonController {

    @Autowired
    private ChansonService chansonService;
 

    /**
     * Get all songs
     * @return the list of songs
     */
    @Operation(summary = "Liste des chansons", description = "Récupère toutes les chansons avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @GetMapping
    public ResponseEntity<Page<Map<String, Object>>> getAllSongs(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Map<String, Object>> chansons = chansonService.getAllChansonsWithAudio(pageable);
        return ResponseEntity.ok(chansons);
    }

    /**
     * Search songs by title
     * @param title the title of the song
     * @return the list of songs
     */
    @Operation(summary = "Recherche de chansons par titre", description = "Recherche de chansons par titre avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Aucune chanson trouvée", content = @Content(mediaType = "application/json", schema = @Schema()))
    @GetMapping("/searchByTitle")
    public ResponseEntity<Page<ChansonResponse>> searchByTitle(@Valid @RequestParam String title, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<ChansonResponse> chansons = chansonService.getAllChansonsByTitle(title, pageable);
        return ResponseEntity.ok(chansons);
    }

    /**
     * Search songs by album
     * @param album the album of the song
     * @return the list of songs
     */
    @Operation(summary = "Recherche de chansons par album", description = "Recherche de chansons par album avec pagination et tri")
    @ApiResponse(responseCode = "200", description = "Chansons récupérées avec succès", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Aucune chanson trouvée", content = @Content(mediaType = "application/json", schema = @Schema()))
    @GetMapping("/searchByAlbum")
    public ResponseEntity<Page<ChansonResponse>> searchByAlbum(@Valid @RequestParam String album, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<ChansonResponse> chansons = chansonService.getAllChansonsByAlbum(album, pageable);
        return ResponseEntity.ok(chansons);
    }

    /**
     * Stream audio file
     * @param id the song id
     * @return the audio file stream
     */
    @Operation(summary = "Stream audio", description = "Stream le fichier audio d'une chanson")
    @ApiResponse(responseCode = "200", description = "Audio streamé avec succès")
    @ApiResponse(responseCode = "404", description = "Audio non trouvé")
    @GetMapping("/{id}/stream")
    public ResponseEntity<Resource> streamAudio(@PathVariable String id) {
        Resource audioFile = chansonService.getAudioFile(id);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType("audio/mpeg"))
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + audioFile.getFilename() + "\"")
            .body(audioFile);
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<Page<Map<String, Object>>>  getChansonsByAlbumId(@PathVariable String albumId, @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "id") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        Page<Map<String, Object>> chansons = chansonService.getAllChansonsByAlbumId(albumId, pageable);
        return ResponseEntity.ok(chansons);
    }
}

