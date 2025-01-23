package com.example.MusicStream.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.MusicStream.model.Album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AlbumRepository extends MongoRepository<Album, String> {

        Optional<Album> findById(String id);
    
    Page<Album> findByTitleContaining(String title, Pageable pageable);
    Page<Album> findByArtistContaining(String artist, Pageable pageable);
    Page<Album> findByReleaseYearBetween(int startYear, int endYear, Pageable pageable);
    
}
