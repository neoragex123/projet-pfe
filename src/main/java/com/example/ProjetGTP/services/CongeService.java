package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.CongeRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class CongeService {
    @Autowired
    private CongeRepository congeRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // ✅ Création d'une demande de congé
    public Conge creerConge(Conge conge, String email) {
        Utilisateur demandeur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        conge.setDemandeur(demandeur);
        conge.setStatut(StatutConge.EN_ATTENTE);
        conge.setDateDemande(LocalDate.now());

        return congeRepository.save(conge);
    }

    // ✅ Liste des congés de l'utilisateur connecté
    public List<Conge> getCongesUtilisateur(String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return congeRepository.findByDemandeur(user);
    }

    // ✅ Liste complète (RH)
    public List<Conge> getAll() {
        return congeRepository.findAll();
    }

    // ✅ Valider ou refuser une demande
    public Conge changerStatut(Long id, StatutConge statut) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));
        conge.setStatut(statut);
        return congeRepository.save(conge);
    }

    public Conge getCongeById(Long id) {
        return congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));
    }

    public Conge updateConge(Conge conge) {
        return congeRepository.save(conge);
    }

}
