package com.example.MusicStream.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.MusicStream.Exception.ResponseException;
import com.example.MusicStream.dto.request.ChansonRequest;
import com.example.MusicStream.dto.response.ChansonResponse;
import com.example.MusicStream.mapper.ChansonMapper;
import com.example.MusicStream.model.Album;
import com.example.MusicStream.model.Chanson;
import com.example.MusicStream.repository.ChansonRepository;
import com.example.MusicStream.service.AlbumService;
import com.example.MusicStream.service.ChansonService;

@Service
public class ChansonServiceImpl implements ChansonService {
    

    @Autowired
    private ChansonRepository chansonRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ChansonMapper chansonMapper;

    @Override
    public ChansonResponse createChanson(ChansonRequest request) {
        Chanson chanson = chansonMapper.toEntity(request);
        Album album = albumService.getAlbumById(request.getAlbumId());
        
        chanson.setAlbum(album);
        List<Chanson> chansons = album.getChansons();
        
        if (chansons != null && chansons.stream().anyMatch(s -> s.getTrackNumber() == request.getTrackNumber())) {
            throw new ResponseException("Le numéro de piste est déjà utilisé", HttpStatus.BAD_REQUEST);
        }
        Chanson savedchanson = chansonRepository.save(chanson);
        chansons.add(savedchanson);
        album.setChansons(chansons);
        albumService.updateAlbum(album);
        return chansonMapper.toResponse(savedchanson);
    }

    @Override
    public ChansonResponse updateChanson(String id, ChansonRequest request) {
        Chanson chanson = chansonRepository.findById(id).orElseThrow(() -> new ResponseException("Chanson non trouvée", HttpStatus.NOT_FOUND));
        if (request.getTitle() == null && request.getDuration() == null && request.getTrackNumber() == null) {
            throw new ResponseException("Aucune information fournie", HttpStatus.BAD_REQUEST);
        }
        if (request.getTitle() != null) {
            chanson.setTitle(request.getTitle());
        }
        if (request.getDuration() != null) {
            chanson.setDuration(request.getDuration());
        }
        if (request.getTrackNumber() != null) {
            if(chanson.getTrackNumber()!=request.getTrackNumber()){
                Album album = albumService.getAlbumById(chanson.getAlbum().getId());
                List<Chanson> chansons = album.getChansons();

                if(chansons!=null && chansons.stream().anyMatch(s -> s.getTrackNumber() == request.getTrackNumber())){
                    throw new ResponseException("Le numéro de piste est déjà utilisé", HttpStatus.BAD_REQUEST);
                }
            }
            chanson.setTrackNumber(request.getTrackNumber());
        }
        Chanson updatedchanson = chansonRepository.save(chanson);
        return chansonMapper.toResponse(updatedchanson);
    }

    @Override
    public boolean deleteChanson(String id) {
        Chanson chanson = chansonRepository.findById(id).orElseThrow(() -> new ResponseException("Chanson non trouvée", HttpStatus.NOT_FOUND));
        chansonRepository.delete(chanson);
        Album album = albumService.getAlbumById(chanson.getAlbum().getId());
        List<Chanson> chansons = album.getChansons();
        chansons.remove(chanson);
        album.setChansons(chansons);
        albumService.updateAlbum(album);
        return true;
    }


    // client fonctions
    @Override
    public Page<ChansonResponse> getAllChansons(Pageable pageable) {
        Page<Chanson> chansons = chansonRepository.findAll(pageable);
        return chansons.map(chansonMapper::toResponse);
    }

    @Override
    public Page<ChansonResponse> getAllChansonsByTitle(String title, Pageable pageable) {
        Page<Chanson> chansons = chansonRepository.findByTitleContaining(title, pageable);
        if(chansons.isEmpty()){
            throw new ResponseException("Aucune chanson trouvée", HttpStatus.NOT_FOUND);
        }
        return chansons.map(chansonMapper::toResponse);
    }


    @Override
    public Page<ChansonResponse> getAllChansonsByAlbum(String album, Pageable pageable) {
        Page<Chanson> chansons = chansonRepository.findByAlbumId(album, pageable);
        return chansons.map(chansonMapper::toResponse);
    }
   

}
