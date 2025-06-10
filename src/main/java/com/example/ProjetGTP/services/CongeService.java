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

    @Autowired
    private HistoriqueService historiqueService;

    // Création d'une demande de congé avec enregistrement dans l'historique
    public Conge creerConge(Conge conge, String email) {
        Utilisateur demandeur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        conge.setUtilisateur(demandeur);
        conge.setStatut(StatutConge.EN_ATTENTE);
        conge.setDateDemande(LocalDate.now());

        Conge saved = congeRepository.save(conge);

        historiqueService.enregistrer(email, "Demande de congé",
                "Création d'une demande de congé du " + conge.getDateDebut() + " au " + conge.getDateFin());

        return saved;
    }

    // Récupérer tous les congés d’un utilisateur (tous statuts)
    public List<Conge> getCongesUtilisateur(String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return congeRepository.findByUtilisateurEmail(email);
    }

    // Récupérer tous les congés (RH, Manager, Admin)
    public List<Conge> getAll() {
        return congeRepository.findAll();
    }

    // Récupérer les congés en attente avec utilisateur chargé (pour la page RH)
    public List<Conge> getCongesEnAttenteAvecUtilisateur() {
        return congeRepository.findByStatutWithUtilisateur(StatutConge.EN_ATTENTE);
    }

    // Changer le statut d’un congé avec enregistrement dans l'historique
    public Conge changerStatut(Long id, StatutConge statut, String validateurEmail) {
        Conge conge = congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));

        conge.setStatut(statut);
        conge.setValidateurEmail(validateurEmail);
        conge.setDateValidation(LocalDate.now());

        Conge saved = congeRepository.save(conge);

        historiqueService.enregistrer(validateurEmail, "Validation congé",
                "Changement de statut du congé n°" + id + " à " + statut.name());

        return saved;
    }

    public Conge getCongeById(Long id) {
        return congeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));
    }

    public Conge updateConge(Conge conge) {
        return congeRepository.save(conge);
    }
}
