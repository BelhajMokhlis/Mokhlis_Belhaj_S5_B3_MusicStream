package com.example.MusicStream.dto.response;

import java.time.Duration;

import lombok.Data;

@Data
public class ChansonSimpleResponse {
    private String title;
    private Duration duration;
    private Integer trackNumber;
   
} 