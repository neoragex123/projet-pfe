package com.example.ProjetGTP.security;

import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;


@Component
public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                    .orElse(null);

            if (user != null) {
                pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                        .ifPresent(pointage -> {
                            if (pointage.getHeureDepart() == null) {
                                pointage.setHeureDepart(LocalTime.now());
                                pointageRepository.save(pointage);
                            }
                        });
            }
        }
    }
}
