package com.example.ProjetGTP.services;


import com.example.ProjetGTP.dtos.UtilisateurDTO;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UtilisateurService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utilisateur createUser(UtilisateurDTO dto) {
        if (utilisateurRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur user = new Utilisateur();
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setEmail(dto.getEmail());
        user.setMotDePasse(passwordEncoder.encode(dto.getMotDePasse()));
        user.setRole(dto.getRole());
        user.setChangementMotDePasse(true);
        user.setDateEmbauche(dto.getDateEmbauche());


        return utilisateurRepository.save(user);
    }

    public List<Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

}
