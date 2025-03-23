package com.example.ProjetGTP.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@Table(name = "conge")
@AllArgsConstructor
@NoArgsConstructor
public class Conge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type; // "PAYE", "MALADIE", etc.

    @Enumerated(EnumType.STRING)
    private StatutConge statut; // EN_ATTENTE, VALIDE, REFUSE

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
