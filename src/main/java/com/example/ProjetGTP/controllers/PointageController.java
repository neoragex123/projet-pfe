package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/pointage")
public class PointageController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @GetMapping("/aujourdhui")
    public ResponseEntity<Pointage> getTodayPointage(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Pointage p = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun pointage pour aujourd’hui."));

        return ResponseEntity.ok(p);
    }
}
