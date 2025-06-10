package com.example.ProjetGTP.controllers;


import com.example.ProjetGTP.entities.Planning;

import com.example.ProjetGTP.services.PlanningService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {
    @Autowired
    private PlanningService planningService;

    // 👤 Employé : consulter son planning
    @GetMapping("/mes")
    public List<Planning> getMonPlanning(Authentication auth) {
        return planningService.getPlanningForUser(auth.getName());
    }

    // 👨‍💼 Manager : créer un planning
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public Planning creerPlanning(@RequestBody Planning planning) {
        return planningService.create(planning);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/semaine")
    public ResponseEntity<?> creerPlanningSemaine(@RequestBody List<Planning> plannings) {
        List<Planning> resultats = new ArrayList<>();
        for (Planning p : plannings) {
            resultats.add(planningService.create(p));
        }
        return ResponseEntity.ok(resultats);
    }

    // (optionnel) RH/Admin
    @PreAuthorize("hasAnyRole('MANAGER', 'RH', 'SUPER_ADMIN')")
    @GetMapping("/tous")
    public List<Planning> getTousLesPlannings() {
        return planningService.getAll();
    }

}
