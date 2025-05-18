package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CongeRepository extends JpaRepository<Conge, Long> {
    List<Conge> findByDemandeur(Utilisateur utilisateur);
}
