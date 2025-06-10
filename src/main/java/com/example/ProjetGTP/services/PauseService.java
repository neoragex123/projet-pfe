package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Pause;
import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PauseRepository;
import com.example.ProjetGTP.repositories.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class PauseService {

    @Autowired
    private PauseRepository pauseRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private HistoriqueService historiqueService;

    // Enregistre le début d'une pause avec historique
    public void enregistrerPause(Utilisateur utilisateur, String type) {
        LocalDate today = LocalDate.now();

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(utilisateur, today)
                .orElseThrow(() -> new RuntimeException("Pointage introuvable pour aujourd’hui."));

        Pause pause = new Pause();
        pause.setType(type);
        pause.setHeureDebut(LocalTime.now());
        pause.setPointage(pointage);

        pauseRepository.save(pause);

        historiqueService.enregistrer(utilisateur.getEmail(), "Début pause",
                "Début de pause " + type + " à " + pause.getHeureDebut().toString());
    }

    // Clôture la dernière pause en cours avec historique
    public void cloturerDernierePause(Pointage pointage) {
        List<Pause> pauses = pauseRepository.findByPointage(pointage);

        Pause derniere = pauses.stream()
                .filter(p -> p.getHeureFin() == null)
                .reduce((first, second) -> second) // dernière pause ouverte
                .orElseThrow(() -> new RuntimeException("Aucune pause en cours."));

        derniere.setHeureFin(LocalTime.now());
        pauseRepository.save(derniere);

        historiqueService.enregistrer(pointage.getUtilisateur().getEmail(), "Fin pause",
                "Fin de pause " + derniere.getType() + " à " + derniere.getHeureFin().toString());
    }
}
