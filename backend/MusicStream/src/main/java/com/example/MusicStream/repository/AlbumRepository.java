package com.example.MusicStream.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.MusicStream.model.Album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface AlbumRepository extends MongoRepository<Album, String> {

        Optional<Album> findById(String id);

        @Aggregation(pipeline = {
            "{ $lookup: { from: 'chansons', localField: '_id', foreignField: 'albumId', as: 'chansons' } }",
            "{ $skip: ?#{#pageable.offset} }",
            "{ $limit: ?#{#pageable.pageSize} }"
        })
        Slice<Album> findAlbumsWithChansons(Pageable pageable);
        
    Page<Album> findByTitleContaining(String title, Pageable pageable);
    Page<Album> findByArtistContaining(String artist, Pageable pageable);
    Page<Album> findByReleaseYearBetween(int startYear, int endYear, Pageable pageable);
    
}
