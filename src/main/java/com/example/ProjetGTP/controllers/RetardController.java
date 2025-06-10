package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/retards")
public class RetardController {

    @Autowired
    private PointageRepository pointageRepository;

    @GetMapping("/aujourdhui")
    @PreAuthorize("hasAnyRole('RH', 'MANAGER', 'SUPER_ADMIN')")
    public List<Pointage> getRetardsAujourdhui() {
        return pointageRepository.findByDateAndEnRetardTrue(LocalDate.now());
    }

    @GetMapping("/tous")
    @PreAuthorize("hasAnyRole('RH', 'MANAGER', 'SUPER_ADMIN')")
    public List<Pointage> getTousLesRetards() {
        return pointageRepository.findAll().stream()
                .filter(Pointage::isEnRetard)
                .toList();
    }
    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('RH', 'MANAGER', 'SUPER_ADMIN')")
    public Map<String, Long> getNbRetardsParUtilisateur() {
        List<Pointage> retards = pointageRepository.findAll().stream()
                .filter(Pointage::isEnRetard)
                .toList();

        Map<String, Long> stats = new HashMap<>();

        for (Pointage p : retards) {
            String nomComplet = p.getUtilisateur().getPrenom() + " " + p.getUtilisateur().getNom();
            stats.put(nomComplet, stats.getOrDefault(nomComplet, 0L) + 1);
        }

        return stats;
    }



    @GetMapping("/periode")
    @PreAuthorize("hasAnyRole('RH', 'MANAGER', 'SUPER_ADMIN')")
    public List<Pointage> getRetardsParPeriode(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        return pointageRepository.findAll().stream()
                .filter(p -> p.isEnRetard() && !p.getDate().isBefore(start) && !p.getDate().isAfter(end))
                .toList();
    }

    @GetMapping("/alertes")
    @PreAuthorize("hasAnyRole('RH', 'MANAGER', 'SUPER_ADMIN')")
    public List<Map<String, Object>> getAlertesRetards() {
        List<Pointage> retards = pointageRepository.findAll()
                .stream().filter(Pointage::isEnRetard).toList();

        Map<Utilisateur, Long> compteur = new HashMap<>();
        for (Pointage p : retards) {
            compteur.put(p.getUtilisateur(), compteur.getOrDefault(p.getUtilisateur(), 0L) + 1);
        }

        List<Map<String, Object>> alertes = new ArrayList<>();
        for (Map.Entry<Utilisateur, Long> entry : compteur.entrySet()) {
            if (entry.getValue() >= 3) { // seuil configurable
                Map<String, Object> a = new HashMap<>();
                a.put("nom", entry.getKey().getPrenom() + " " + entry.getKey().getNom());
                a.put("email", entry.getKey().getEmail());
                a.put("retards", entry.getValue());
                alertes.add(a);
            }
        }
        return alertes;
    }


}
