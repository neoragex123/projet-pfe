package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Planning;
import com.example.ProjetGTP.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
    List<Planning> findByUtilisateurAndDateBetweenOrderByDateAsc(Utilisateur utilisateur, LocalDate start, LocalDate end);
    boolean existsByUtilisateurAndDate(Utilisateur utilisateur, LocalDate date);
    Optional<Planning> findByUtilisateurAndDate(Utilisateur utilisateur, LocalDate date);

}
