package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.dtos.UtilisateurDTO;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import com.example.ProjetGTP.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping
    public ResponseEntity<Utilisateur> createUser(@RequestBody UtilisateurDTO dto) {
        Utilisateur created = utilisateurService.createUser(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Utilisateur>> listUsers() {
        return ResponseEntity.ok(utilisateurService.getAllUsers());
    }
    @GetMapping("/me")
    public ResponseEntity<UtilisateurDTO> getConnectedUser(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return ResponseEntity.ok(dto);
    }

}
