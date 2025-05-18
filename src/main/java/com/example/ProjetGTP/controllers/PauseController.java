package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import com.example.ProjetGTP.services.PauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pauses")
public class PauseController {

    @Autowired
    private PauseService pauseService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @PostMapping("/debut")
    public void demarrerPause(@RequestParam String type, Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        pauseService.enregistrerPause(user, type);
    }

    @PostMapping("/fin")
    public void finPause(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(
                user, java.time.LocalDate.now()
        ).orElseThrow(() -> new RuntimeException("Aucun pointage trouvé pour aujourd’hui."));

        pauseService.cloturerDernierePause(pointage);
    }
}
