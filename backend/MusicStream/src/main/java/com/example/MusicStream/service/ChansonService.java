package com.example.MusicStream.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.MusicStream.dto.request.ChansonRequest;
import com.example.MusicStream.dto.response.ChansonResponse;


public interface ChansonService {
    // admin fonctions
    ChansonResponse createChanson(ChansonRequest request);
    ChansonResponse updateChanson(String id, ChansonRequest request);
    boolean deleteChanson(String id);

    // client fonctions

    Page<ChansonResponse> getAllChansons(Pageable pageable);
    Page<ChansonResponse> getAllChansonsByTitle(String title, Pageable pageable);
    Page<ChansonResponse> getAllChansonsByAlbum(String album, Pageable pageable);
  
}