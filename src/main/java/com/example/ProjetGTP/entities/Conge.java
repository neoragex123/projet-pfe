package com.example.ProjetGTP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;

    @Enumerated(EnumType.STRING)
    private TypeConge type;

    @Enumerated(EnumType.STRING)
    private StatutConge statut = StatutConge.EN_ATTENTE;

    private String commentaire;

    private String justificatifPath; // chemin du fichier PDF ou image

    @ManyToOne
    private Utilisateur demandeur;

    private LocalDate dateDemande;
}
