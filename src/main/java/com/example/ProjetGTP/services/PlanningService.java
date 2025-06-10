package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Planning;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PlanningRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PlanningService {

    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public Planning create(Planning planning) {
        if (planning.getUtilisateur() == null || planning.getUtilisateur().getId() == null) {
            throw new RuntimeException("Utilisateur manquant");
        }

        Utilisateur user = utilisateurRepository.findById(planning.getUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        planning.setUtilisateur(user);
        return planningRepository.save(planning);
    }

    public List<Planning> getPlanningForUser(String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        LocalDate aujourdHui = LocalDate.now();
        LocalDate debut = aujourdHui.minusDays(15);
        LocalDate fin = aujourdHui.plusDays(15);

        return planningRepository.findByUtilisateurAndDateBetweenOrderByDateAsc(user, debut, fin);
    }

    public List<Planning> getAll() {
        return planningRepository.findAll();
    }
}

