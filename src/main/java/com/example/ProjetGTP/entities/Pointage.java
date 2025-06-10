package com.example.ProjetGTP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pointage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime heureArrivee;

    private LocalTime heureDepart;

    @ManyToOne(fetch = FetchType.EAGER)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "pointage", cascade = CascadeType.ALL)
    private List<Pause> pauses;

    @Column(nullable = false)
    private boolean enRetard = false;
}
