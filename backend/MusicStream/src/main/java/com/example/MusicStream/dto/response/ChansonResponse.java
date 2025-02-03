package com.example.MusicStream.dto.response;
import java.time.Duration;
import java.util.Date;

import lombok.Data;

@Data
public class ChansonResponse {
	private String id;
    private String title;
    private Duration duration;
    private Integer trackNumber;
    private String description;
    private String categorie;
    private String audioFileId;
    private Date dateAjout;
    private AlbumSimpleResponse album;
    
  
} 