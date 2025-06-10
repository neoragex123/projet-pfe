package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.ActionHistorique;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.ActionHistoriqueRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoriqueService {

    @Autowired
    private ActionHistoriqueRepository repo;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<ActionHistorique> getHistoriqueByEmail(String email) {
        return repo.findByUtilisateurEmailOrderByDateActionDesc(email);
    }

    public void enregistrer(String email, String type, String description) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        ActionHistorique action = new ActionHistorique();
        action.setUtilisateur(user);
        action.setTypeAction(type);
        action.setDateAction(LocalDateTime.now());
        action.setDescription(description);

        repo.save(action);
    }
}
