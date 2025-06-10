package com.example.ProjetGTP.dtos;

import com.example.ProjetGTP.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;
    private LocalDate dateEmbauche;



}
