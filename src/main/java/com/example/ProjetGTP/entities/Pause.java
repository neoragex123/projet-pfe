package com.example.ProjetGTP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pause {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @ManyToOne
    private Pointage pointage;
}
