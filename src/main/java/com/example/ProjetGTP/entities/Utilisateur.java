package com.example.ProjetGTP.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor

@Data

public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean changementMotDePasse = false;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private LocalDate dateEmbauche;


}

