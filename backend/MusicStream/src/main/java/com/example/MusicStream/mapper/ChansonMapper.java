package com.example.MusicStream.mapper;

import com.example.MusicStream.dto.request.ChansonRequest;
import com.example.MusicStream.dto.response.ChansonResponse;
import com.example.MusicStream.dto.response.ChansonSimpleResponse;
import com.example.MusicStream.model.Chanson;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChansonMapper {
    
    @Mapping(target = "album.id", source = "albumId")
    @Mapping(target = "dateAjout", ignore = true)
    Chanson toEntity(ChansonRequest request);
    
    @Mapping(target = "album", source = "album")
    ChansonResponse toResponse(Chanson Chanson);

    @Named("toSimpleResponseList")
    List<ChansonSimpleResponse> toSimpleResponseList(List<Chanson> Chansons);


}