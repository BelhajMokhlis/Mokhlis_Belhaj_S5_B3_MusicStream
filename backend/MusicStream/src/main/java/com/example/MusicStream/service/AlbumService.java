package com.example.MusicStream.service;

import com.example.MusicStream.dto.request.AlbumRequest;
import com.example.MusicStream.dto.response.AlbumResponse;
import com.example.MusicStream.model.Album;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AlbumService {
    // admin fonction
    AlbumResponse createAlbum(AlbumRequest request);
    AlbumResponse updateAlbum(String id, AlbumRequest request);
    void deleteAlbum(String id);

    // all users fonction
    Slice<AlbumResponse> getAllAlbums(Pageable pageable);
    Page<AlbumResponse> getAlbumsByTitle(String title, Pageable pageable);
    Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable);
    Page<AlbumResponse> filterAlbumsByYear(int startYear, int endYear, Pageable pageable);

    // need it for song creation
    Album getAlbumById(String id);
    Album updateAlbum(Album album);
}
