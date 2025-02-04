package com.example.MusicStream.service;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.MusicStream.dto.request.ChansonRequest;
import com.example.MusicStream.dto.response.ChansonResponse;


public interface ChansonService {
    // admin fonctions
    ChansonResponse createChanson(ChansonRequest request, MultipartFile file);
    ChansonResponse updateChanson(String id, ChansonRequest request);
    boolean deleteChanson(String id);

    // client fonctions

    Page<ChansonResponse> getAllChansons(Pageable pageable);
    Page<ChansonResponse> getAllChansonsByTitle(String title, Pageable pageable);
    Page<ChansonResponse> getAllChansonsByAlbum(String album, Pageable pageable);
    Page<Map<String, Object>> getAllChansonsByAlbumId(String albumId, Pageable pageable);

    Resource getAudioFile(String chansonId);
  
   Page<Map<String, Object>> getAllChansonsWithAudio(Pageable pageable);
}