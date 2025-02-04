package com.example.MusicStream.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.Duration;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.core.query.Criteria;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

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

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Override
    public ChansonResponse createChanson(ChansonRequest request, MultipartFile audioFile) {
        try {
            // 1. Stocker le fichier audio dans GridFS
            ObjectId fileId = gridFsTemplate.store(
                audioFile.getInputStream(),
                audioFile.getOriginalFilename(),
                audioFile.getContentType()
            );

            // 2. Créer l'entité Chanson
            Chanson chanson = chansonMapper.toEntity(request);
            chanson.setAudioFileId(fileId.toString());
              // 3. Calculer la durée du fichier audio
        Duration duration = getAudioDuration(audioFile); 
        chanson.setDuration(duration); 



           
            // 3. Vérifier et associer l'album
            Album album = albumService.getAlbumById(request.getAlbumId());
            chanson.setAlbum(album);
            // 4. Sauvegarder la chanson
            Chanson savedChanson = chansonRepository.save(chanson);
            
            return chansonMapper.toResponse(savedChanson);
        } catch (IOException e) {
            throw new ResponseException("Erreur lors du stockage du fichier audio", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Resource getAudioFile(String chansonId) {
        Chanson chanson = chansonRepository.findById(chansonId)
            .orElseThrow(() -> new ResponseException("Chanson non trouvée", HttpStatus.NOT_FOUND));

        try {
            GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(chanson.getAudioFileId())));
            if (gridFSFile == null) {
                throw new ResponseException("Fichier audio non trouvé", HttpStatus.NOT_FOUND);
            }

            GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
            return resource;
        } catch (Exception e) {
            throw new ResponseException("Erreur lors de la récupération du fichier audio", HttpStatus.NOT_FOUND);
        }
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
        if(chansons.isEmpty()){
            throw new ResponseException("Aucune chanson trouvée", HttpStatus.NOT_FOUND);
        }
        return chansons.map(chansonMapper::toResponse);
    }

    @Override
    public Page<Map<String, Object>> getAllChansonsByAlbumId(String albumId, Pageable pageable) {
        Page<Chanson> chansons = chansonRepository.findByAlbumId(albumId, pageable);
        return chansons.map(chanson -> {
            Map<String, Object> response = new HashMap<>();
            response.put("metadata", chansonMapper.toResponse(chanson));
            response.put("audioUrl", "/api/users/chanson/" + chanson.getId() + "/stream");
            return response;
        });
    }
   

    @Override
    public Page<Map<String, Object>> getAllChansonsWithAudio(Pageable pageable) {
        Page<Chanson> chansons = chansonRepository.findAll(pageable);
        return chansons.map(chanson -> {
            Map<String, Object> response = new HashMap<>();
            response.put("metadata", chansonMapper.toResponse(chanson));
            response.put("audioUrl", "/api/users/chanson/" + chanson.getId() + "/stream");
            return response;
        });
    }

    private Duration getAudioDuration(MultipartFile audioFile) throws IOException {
        long fileSize = audioFile.getSize();
    
        int bitrate = 128000; 
        
        long durationInSeconds = (fileSize * 8) / bitrate; 
        return Duration.ofSeconds(durationInSeconds);
    }

    

}
