package com.example.MusicStream.controller.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.MusicStream.dto.response.AlbumResponse;
import com.example.MusicStream.mapper.AlbumMapper;
import com.example.MusicStream.model.Album;
import com.example.MusicStream.service.AlbumService;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/api/users/albums")
@Tag(name = "Users Album Controller", description = "Gestion des albums par les utilisateurs")
public class UsersAlbumController {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumMapper albumMapper;

    /** 
     * Get all albums
     * @param page the page number
     * @param size the page size
     * @param sortBy the field to sort by
     * @param sortOrder the sort order
     * @return the page of albums
     * **/
    @GetMapping
    @Operation(summary = "Get all albums", description = "Retrieves all albums with pagination and sorting options")
    @ApiResponse(responseCode = "200", description = "Albums successfully retrieved", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Slice.class)))
    public Slice<AlbumResponse> getAllAlbums(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sortBy,
                                            @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return albumService.getAllAlbums(pageable);
    }

    /** 
     * Get albums by title
     * @param title the title of the album
     * @param page the page number
     * @param size the page size
     * @param sortBy the field to sort by
     * @param sortOrder the sort order
     * @return the page of albums
     * **/
    @GetMapping("/searchByTitle")
    @Operation(summary = "trouve les albums par titre", description = "trouve les albums par titre avec pagination et options de tri")
    @ApiResponse(responseCode = "200", description = "albums trouvés", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "Albums not found", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "albums non trouvés",
            "status": 404
        }
        """)))
    @ApiResponse(responseCode = "400", description = "paramètre de requête invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "le paramètre requis est manquant: title",
            "status": 400
        }
        """)))
    public Page<AlbumResponse> getAlbumsByTitle(@Valid @RequestParam(required = true) String title,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return albumService.getAlbumsByTitle(title, pageable);
    }


    /** 
     * Get albums by artist
     * @param artist the artist of the album
     * @param page the page number
     * @param size the page size
     * @param sortBy the field to sort by
     * @param sortOrder the sort order
     * @return the page of albums
     * **/
    @GetMapping("/searchByArtist")
    @Operation(summary = "trouve les albums par artiste", description = "trouve les albums par artiste avec pagination et options de tri")
    @ApiResponse(responseCode = "200", description = "albums trouvés", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "404", description = "albums non trouvés", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "albums non trouvés",
            "status": 404
        }
        """)))
    @ApiResponse(responseCode = "400", description = "paramètre de requête invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "paramètre de requête invalide : artist",
            "status": 400
        }
        """)))
    public Page<AlbumResponse> getAlbumsByArtist(@Valid @RequestParam(required = true) String artist,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size,
                                                @RequestParam(defaultValue = "id") String sortBy,
                                                @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return albumService.getAlbumsByArtist(artist, pageable);
    }


    
    /** 
     * Filter albums by year
     * @param startYear the start year
     * @param endYear the end year
     * @param page the page number
     * @param size the page size
     * @param sortBy the field to sort by
     * @param sortOrder the sort order
     * @return the page of albums
     * **/
    @GetMapping("/filterByYear")
    @Operation(summary = "filtre les albums par année de sortie", description = "trouve les albums par année de sortie entre 1980 et 2024")
    @ApiResponse(responseCode = "200", description = "albums trouvés", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "400", description = "année de sortie invalide", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "L'année de sortie doit être entre 1980 et 2024",
            "status": 400
        }
        """)))
    @ApiResponse(responseCode = "404", description = "albums non trouvés", content = @Content(mediaType = "application/json", schema = @Schema(example = """
        {
            "message": "albums non trouvés",
            "status": 404
        }
        """)))
        
        public Page<AlbumResponse> filterAlbumsByYear(
            @RequestParam(defaultValue = "1980") @Min(1980) @Max(2024)  int startYear,
            @RequestParam(defaultValue = "2024") @Min(1980) @Max(2024)  int endYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortOrder), sortBy));
        return albumService.filterAlbumsByYear(startYear, endYear, pageable);
    }



    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable String id) {
        Album album = albumService.getAlbumById(id);
        AlbumResponse response = albumMapper.toResponse(album);
        return ResponseEntity.ok(response);
    }

}
