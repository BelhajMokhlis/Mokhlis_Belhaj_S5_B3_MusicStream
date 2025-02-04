package com.example.MusicStream.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.MusicStream.model.Chanson;

import java.util.List;


public interface ChansonRepository extends MongoRepository<Chanson, String> {

    // get the Chansons by album id

    // for the client
    Page<Chanson> findByTitleContaining(String title, Pageable pageable);
    Page<Chanson> findByAlbumId(String albumId, Pageable pageable);
    
}
