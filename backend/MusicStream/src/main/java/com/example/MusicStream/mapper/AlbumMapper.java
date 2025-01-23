package com.example.MusicStream.mapper;

import com.example.MusicStream.dto.request.AlbumRequest;
import com.example.MusicStream.dto.response.AlbumResponse;
import com.example.MusicStream.model.Album;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {ChansonMapper.class})
public interface AlbumMapper {

    @Mapping(target = "chansons", ignore = true)
    Album toEntity(AlbumRequest request);
    
    @Mapping(target = "chansons", source = "chansons")
    AlbumResponse toResponse(Album album);
} 