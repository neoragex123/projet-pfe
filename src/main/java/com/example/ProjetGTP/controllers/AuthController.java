package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import com.example.ProjetGTP.services.PointageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageService pointageService;

    @GetMapping("/welcome")
    public String welcome(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return "redirect:/login.html?error";
        }

        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElse(null);

        if (user == null) {
            return "redirect:/login.html?error";
        }



        // ✅ Forcer le changement de mot de passe si nécessaire
        if (user.isChangementMotDePasse()) {
            return "redirect:/changepassword.html";
        }

        // ✅ Sinon, rediriger vers l'accueil
        return "redirect:/home.html";
    }
}