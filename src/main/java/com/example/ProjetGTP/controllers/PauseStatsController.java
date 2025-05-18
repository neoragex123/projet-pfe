package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pause;
import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PauseRepository;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/pause-stats")
public class PauseStatsController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private PauseRepository pauseRepository;

    // Durée totale des pauses du jour pour l'utilisateur connecté
    @GetMapping("/duree-aujourdhui")
    public long getDureeTotalePauses(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun pointage"));

        List<Pause> pauses = pauseRepository.findByPointage(pointage);

        return pauses.stream()
                .filter(p -> p.getHeureDebut() != null && p.getHeureFin() != null)
                .mapToLong(p -> ChronoUnit.MINUTES.between(p.getHeureDebut(), p.getHeureFin()))
                .sum();
    }

    // Liste complète des pauses du jour (optionnel pour RH ou affichage)
    @GetMapping("/liste")
    public List<Pause> getPausesDuJour(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun pointage"));

        return pauseRepository.findByPointage(pointage);
    }
}
