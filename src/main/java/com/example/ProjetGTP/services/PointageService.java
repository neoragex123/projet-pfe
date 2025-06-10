package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PlanningRepository;
import com.example.ProjetGTP.repositories.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PointageService {

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private HistoriqueService historiqueService;

    public void enregistrerHeureArrivee(Utilisateur utilisateur) {
        LocalDate today = LocalDate.now();

        // Évite de recréer un pointage
        if (pointageRepository.findByUtilisateurAndDate(utilisateur, today).isEmpty()) {
            Pointage p = new Pointage();
            p.setUtilisateur(utilisateur);
            p.setDate(today);
            p.setHeureArrivee(LocalTime.now());

            // ⚠️ Comparaison avec le planning
            planningRepository.findByUtilisateurAndDate(utilisateur, today).ifPresent(planning -> {
                if (p.getHeureArrivee().isAfter(planning.getHeureEntree())) {
                    p.setEnRetard(true);  // ✅ Calcul du retard ici
                }
            });

            pointageRepository.save(p);

            // Enregistrement dans l'historique
            historiqueService.enregistrer(utilisateur.getEmail(), "Pointage arrivée",
                    "Pointage d'arrivée effectué le " + today.toString() + " à " + p.getHeureArrivee().toString());
        }
    }
}
