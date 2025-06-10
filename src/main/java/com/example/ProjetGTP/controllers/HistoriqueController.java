package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.entities.ActionHistorique;
import com.example.ProjetGTP.services.HistoriqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/historique")
public class HistoriqueController {

    @Autowired
    private HistoriqueService service;

    @GetMapping("/me")
    public List<ActionHistorique> getMyActions(Authentication auth) {
        return service.getHistoriqueByEmail(auth.getName());
    }
}