package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PointageService {
    @Autowired
    private PointageRepository pointageRepository;

    public void enregistrerHeureArrivee(Utilisateur utilisateur) {
        LocalDate today = LocalDate.now();
        // Évite de recréer le pointage s'il existe déjà
        if (pointageRepository.findByUtilisateurAndDate(utilisateur, today).isEmpty()) {
            Pointage p = new Pointage();
            p.setUtilisateur(utilisateur);
            p.setDate(today);
            p.setHeureArrivee(LocalTime.now());
            pointageRepository.save(p);
        }
    }
}
