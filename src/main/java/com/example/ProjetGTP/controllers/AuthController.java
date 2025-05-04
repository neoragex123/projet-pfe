package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthController {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping("/welcome")
    public String welcome(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));

        if (user.isChangementMotDePasse()) {
            // Redirige vers la page de changement de mot de passe
            return "redirect:/changepassword.html";
        }

        // Sinon, aller sur la page normale d'accueil
        return "redirect:/home.html";
    }

}
