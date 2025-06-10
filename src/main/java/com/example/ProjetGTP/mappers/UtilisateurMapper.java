package com.example.ProjetGTP.mappers;

import com.example.ProjetGTP.dtos.UtilisateurDTO;
import com.example.ProjetGTP.entities.Utilisateur;

public class UtilisateurMapper {

    public static UtilisateurDTO toDTO(Utilisateur user) {
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setMotDePasse(user.getMotDePasse());
        dto.setRole(user.getRole());
        dto.setDateEmbauche(user.getDateEmbauche());
        return dto;
    }

    public static Utilisateur toEntity(UtilisateurDTO dto) {
        Utilisateur user = new Utilisateur();
        user.setId(dto.getId());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setMotDePasse(dto.getMotDePasse());
        user.setRole(dto.getRole());
        user.setDateEmbauche(dto.getDateEmbauche());
        return user;
    }
}
