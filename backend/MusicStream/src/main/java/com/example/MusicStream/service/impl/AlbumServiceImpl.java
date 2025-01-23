package com.example.MusicStream.service.impl;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.MusicStream.Exception.ResponseException;
import com.example.MusicStream.dto.request.AlbumRequest;
import com.example.MusicStream.dto.response.AlbumResponse;
import com.example.MusicStream.mapper.AlbumMapper;
import com.example.MusicStream.model.Album;
import com.example.MusicStream.model.Chanson;
import com.example.MusicStream.repository.AlbumRepository;
import com.example.MusicStream.repository.ChansonRepository;
import com.example.MusicStream.service.AlbumService;




@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ChansonRepository ChansonRepository;

    @Autowired
    private AlbumMapper albumMapper;


    // admin fonction
    @Override
    public AlbumResponse createAlbum(AlbumRequest request) {
        Album album = albumMapper.toEntity(request);
        Album savedAlbum = albumRepository.save(album);
        return albumMapper.toResponse(savedAlbum);
    }

    @Override
    public AlbumResponse updateAlbum(String id, AlbumRequest request) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            throw new ResponseException("Album non trouvé", HttpStatus.NOT_FOUND);
        }

        if(request.getTitle() != null) {
            album.get().setTitle(request.getTitle());
        }
        if(request.getArtist() != null) {
            album.get().setArtist(request.getArtist());
        }
        if (request.getReleaseYear() != null) {
            album.get().setReleaseYear(request.getReleaseYear());
        }
        if(request.getGenre() != null) {
            album.get().setGenre(request.getGenre());
        }
        Album savedAlbum = albumRepository.save(album.get());
       
        return albumMapper.toResponse(savedAlbum);
    }

    @Override
    public void deleteAlbum(String id) {
        Optional<Album> album = albumRepository.findById(id);
        if (album.isEmpty()) {
            throw new ResponseException("Album non trouvé", HttpStatus.NOT_FOUND);
        }
        List<Chanson> Chansons = album.get().getChansons();
        if (Chansons != null) {
            for (Chanson Chanson : Chansons) {
                ChansonRepository.delete(Chanson);
            }
        }
        
        albumRepository.delete(album.get());
    }



    // all users fonction
    @Override
    public Page<AlbumResponse> getAllAlbums(Pageable pageable) {
        Page<Album> albums = albumRepository.findAll(pageable);
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> getAlbumsByTitle(String title, Pageable pageable) {
        Page<Album> albums = albumRepository.findByTitleContaining(title, pageable);
        if (albums.isEmpty()) {
            throw new ResponseException("Aucun album trouvé avec ce titre " + title, HttpStatus.NOT_FOUND);
        }
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> getAlbumsByArtist(String artist, Pageable pageable) {
        Page<Album> albums = albumRepository.findByArtistContaining(artist, pageable);
        if (albums.isEmpty()) {
            throw new ResponseException("Aucun album trouvé de l'artiste: " + artist, HttpStatus.NOT_FOUND);
        }
        return albums.map(albumMapper::toResponse);
    }

    @Override
    public Page<AlbumResponse> filterAlbumsByYear(int startYear, int endYear, Pageable pageable) {
        Page<Album> albums = albumRepository.findByReleaseYearBetween(startYear, endYear, pageable);
        if (albums.isEmpty()) {
            throw new ResponseException("Aucun album trouvé entre les années " + startYear + " et " + endYear, HttpStatus.NOT_FOUND);
        }
        return albums.map(albumMapper::toResponse);
    }


    // need it for Chanson creation
    @Override
    public Album getAlbumById(String id) {
        return albumRepository.findById(id).orElseThrow(()->  new ResponseException("Aucun album trouvé de l'album ", HttpStatus.NOT_FOUND));
    }

    @Override
    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
    }
}
