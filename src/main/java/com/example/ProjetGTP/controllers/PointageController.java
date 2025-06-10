package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;

import com.example.ProjetGTP.services.PointageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pointage")
public class PointageController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PointageRepository pointageRepository;

    @Autowired
    private PointageService pointageService;

    @GetMapping("/aujourdhui")
    public ResponseEntity<?> getPointageDuJour(Authentication auth) {
        Utilisateur user = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/arrivee")
    public ResponseEntity<?> pointerArrivee(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Optional<Pointage> existing = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now());

        if (existing.isPresent() && existing.get().getHeureArrivee() != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Déjà pointé aujourd'hui.");
        }

        pointageService.enregistrerHeureArrivee(user);

        return ResponseEntity.ok("Arrivée enregistrée.");
    }

    @PostMapping("/depart")
    public ResponseEntity<?> pointerDepart(Authentication authentication) {
        Utilisateur user = utilisateurRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Pointage pointage = pointageRepository.findByUtilisateurAndDate(user, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("Aucun pointage trouvé pour aujourd'hui."));

        pointage.setHeureDepart(LocalTime.now());
        pointageRepository.save(pointage);

        return ResponseEntity.ok("Fin de journée enregistrée.");
    }

    @GetMapping("/heures-total")
    @PreAuthorize("hasRole('RH')")
    public ResponseEntity<?> getTotalHeuresTravaillees(
            @RequestParam Long utilisateurId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Optional<Utilisateur> userOpt = utilisateurRepository.findById(utilisateurId);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        List<Pointage> pointages = pointageRepository.findByUtilisateurAndDateBetween(userOpt.get(), start, end);

        long totalMinutes = pointages.stream()
                .filter(p -> p.getHeureArrivee() != null && p.getHeureDepart() != null)
                .mapToLong(p -> Duration.between(p.getHeureArrivee(), p.getHeureDepart()).toMinutes())
                .sum();

        long heures = totalMinutes / 60;
        long minutes = totalMinutes % 60;

        Map<String, Object> result = new HashMap<>();
        result.put("heures", heures);
        result.put("minutes", minutes);
        result.put("utilisateur", userOpt.get().getPrenom() + " " + userOpt.get().getNom());

        return ResponseEntity.ok(result);
    }
}
