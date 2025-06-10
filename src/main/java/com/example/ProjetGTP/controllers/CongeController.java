package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.repositories.CongeRepository;
import com.example.ProjetGTP.services.CongeService;

import com.example.ProjetGTP.services.SoldeCongeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/conges")
public class CongeController {

    @Autowired
    private CongeService congeService;

    @Autowired
    private SoldeCongeService soldeCongeService;

    // Créer une nouvelle demande de congé
    @PostMapping
    public Conge demanderConge(@RequestBody Conge conge, Authentication authentication) {
        return congeService.creerConge(conge, authentication.getName());
    }

    // Voir les congés de l'utilisateur connecté
    @GetMapping("/mes")
    public List<Conge> mesConges(Authentication authentication) {
        return congeService.getCongesUtilisateur(authentication.getName());
    }

    // Voir toutes les demandes (RH ou admin)
    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER','RH','SUPER_ADMIN')")
    public List<Conge> tousLesConges() {
        return congeService.getAll();
    }

    // Voir les demandes EN ATTENTE (pour page RH)
    @GetMapping("/en-attente")
    @PreAuthorize("hasAnyRole('MANAGER','RH','SUPER_ADMIN')")
    public List<Conge> getEnAttente() {
        return congeService.getCongesEnAttenteAvecUtilisateur();
    }

    // Valider ou refuser un congé (workflow RH/Manager)
    @PatchMapping("/{id}/statut")
    @PreAuthorize("hasAnyRole('MANAGER','RH','SUPER_ADMIN')")
    public Conge changerStatutConge(
            @PathVariable Long id,
            @RequestParam StatutConge statut,
            Authentication auth) {
        return congeService.changerStatut(id, statut, auth.getName());
    }

    // Upload du justificatif (après création de la demande)
    @PostMapping("/{id}/justificatif")
    public Conge uploadJustificatif(@PathVariable Long id,
                                    @RequestParam("file") MultipartFile file) throws IOException {

        Conge conge = congeService.getCongeById(id);

        if (file.isEmpty()) {
            throw new RuntimeException("Fichier vide");
        }

        String uploadDir = System.getProperty("user.dir") + "/uploads/justificatifs";
        Files.createDirectories(Paths.get(uploadDir));

        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String filename = "conge_" + id + "_justificatif" + extension;
        Path filepath = Paths.get(uploadDir, filename);

        file.transferTo(filepath.toFile());
        System.out.println("📎 Fichier transféré : " + filepath.toString());

        conge.setJustificatifPath(filepath.toString());

        return congeService.updateConge(conge);
    }

    // Calcul du solde de congés
    @GetMapping("/solde")
    public double getSolde(Authentication authentication) {
        return soldeCongeService.calculerSolde(authentication.getName());
    }
}

