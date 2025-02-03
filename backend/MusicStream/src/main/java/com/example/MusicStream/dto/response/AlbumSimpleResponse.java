package com.example.MusicStream.dto.response;

import lombok.Data;

@Data
public class AlbumSimpleResponse {
    private String id;
    private String title;
    private String artist;
    private Integer releaseYear;
    private String genre;
} 