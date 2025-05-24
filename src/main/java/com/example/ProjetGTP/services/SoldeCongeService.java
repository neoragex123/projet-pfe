package com.example.ProjetGTP.services;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.CongeRepository;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SoldeCongeService {
    @Autowired
    private CongeRepository congeRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public double calculerSolde(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        LocalDate embauche = utilisateur.getDateEmbauche();
        LocalDate today = LocalDate.now();
        Period period = Period.between(embauche.withDayOfMonth(1), today.withDayOfMonth(1));

        int moisTravailles = period.getYears() * 12 + period.getMonths() + 1; // +1 si on veut inclure le mois en cours

        double soldeTotal = moisTravailles * 1.5;

        List<Conge> valides = congeRepository.findByDemandeurEmailAndStatut(email, StatutConge.VALIDE);
        long joursPris = valides.stream()
                .mapToLong(c -> ChronoUnit.DAYS.between(c.getDateDebut(), c.getDateFin()) + 1)
                .sum();

        return soldeTotal - joursPris;
    }
}
