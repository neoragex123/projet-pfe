package com.example.ProjetGTP.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @Column(name = "justificatif_path")
    private String justificatifPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Utilisateur utilisateur;

    private LocalDate dateDemande;

    private String validateurEmail;
    private LocalDate dateValidation;

}
