package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.services.CongeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    // ✅ 1. Créer une nouvelle demande de congé
    @PostMapping
    public Conge demanderConge(@RequestBody Conge conge, Authentication authentication) {
        return congeService.creerConge(conge, authentication.getName());
    }

    // ✅ 2. Voir les demandes de l'utilisateur connecté
    @GetMapping("/mes")
    public List<Conge> mesConges(Authentication authentication) {
        return congeService.getCongesUtilisateur(authentication.getName());
    }

    // ✅ 3. Voir toutes les demandes (RH ou admin)
    @GetMapping
    public List<Conge> tousLesConges() {
        return congeService.getAll();
    }

    // ✅ 4. Valider ou refuser un congé (RH/manager)
    @PatchMapping("/{id}/statut")
    public Conge changerStatut(@PathVariable Long id, @RequestParam StatutConge statut) {
        return congeService.changerStatut(id, statut);
    }


    @PostMapping("/{id}/justificatif")
    public Conge uploadJustificatif(@PathVariable Long id,
                                    @RequestParam("file") MultipartFile file) throws IOException {

        Conge conge = congeService.getCongeById(id);

        if (file.isEmpty()) {
            throw new RuntimeException("Fichier vide");
        }

        String uploadDir = "uploads/justificatifs";
        Files.createDirectories(Paths.get(uploadDir)); // ✅ Cette ligne

        String extension = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf("."));
        String filename = "conge_" + id + "_justificatif" + extension;
        Path filepath = Paths.get(uploadDir, filename);

        file.transferTo(filepath.toFile());

        conge.setJustificatifPath(filepath.toString());
        return congeService.updateConge(conge);
    }
}
