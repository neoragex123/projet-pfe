package com.example.ProjetGTP.dtos;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CongeDto {
    private Long id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type;
    private String statut;
    private Long userId;
}
