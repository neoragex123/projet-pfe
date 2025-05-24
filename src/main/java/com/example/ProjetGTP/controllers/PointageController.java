package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/pointage")
public class PointageController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;


    @GetMapping("/aujourdhui")
    public ResponseEntity<?> getPointageDuJour(Authentication auth) {
        Utilisateur user = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/arrivee")
    public ResponseEntity<?> pointerArrivee(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Optional<Pointage> existing = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now());

        if (existing.isPresent() && existing.get().getHeureArrivee() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Déjà pointé aujourd'hui.");
        }

        Pointage pointage = existing.orElse(new Pointage());
        pointage.setUtilisateur(user);
        pointage.setDate(LocalDate.now());
        pointage.setHeureArrivee(LocalTime.now());

        pointageRepository.save(pointage);
        return ResponseEntity.ok(pointage);
    }

    @PostMapping("/depart")
    public ResponseEntity<?> pointerDepart(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun pointage trouvé pour aujourd'hui."));

        pointage.setHeureDepart(LocalTime.now());
        pointageRepository.save(pointage);

        return ResponseEntity.ok("Fin de journée enregistrée.");
    }
}
