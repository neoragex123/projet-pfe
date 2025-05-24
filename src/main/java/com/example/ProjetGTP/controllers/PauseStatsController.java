package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pause;
import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PauseRepository;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pause-stats")
public class PauseStatsController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private PauseRepository pauseRepository;

    // ✅ Durée totale des pauses du jour
    @GetMapping("/duree-aujourdhui")
    public ResponseEntity<Long> getDureeTotalePauses(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Optional<Pointage> pointageOpt = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now());
        if (pointageOpt.isEmpty()) {
            return ResponseEntity.ok(0L); // Pas encore pointé
        }

        List<Pause> pauses = pauseRepository.findByPointage(pointageOpt.get());

        long total = pauses.stream()
                .filter(p -> p.getHeureDebut() != null && p.getHeureFin() != null)
                .mapToLong(p -> ChronoUnit.MINUTES.between(p.getHeureDebut(), p.getHeureFin()))
                .sum();

        return ResponseEntity.ok(total);
    }

    // ✅ Liste des pauses du jour
    @GetMapping("/liste")
    public ResponseEntity<List<Pause>> getPausesDuJour(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Optional<Pointage> pointageOpt = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now());
        if (pointageOpt.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList()); // Pas encore pointé
        }

        List<Pause> pauses = pauseRepository.findByPointage(pointageOpt.get());
        return ResponseEntity.ok(pauses);
    }
}
