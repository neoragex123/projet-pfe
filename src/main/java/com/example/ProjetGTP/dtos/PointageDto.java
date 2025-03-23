package com.example.ProjetGTP.dtos;

import lombok.Data;

import java.time.LocalDateTime;
@Data

public class PointageDto {
    private Long id;
    private LocalDateTime dateTime;
    private String type;
    private Long userId;
}
