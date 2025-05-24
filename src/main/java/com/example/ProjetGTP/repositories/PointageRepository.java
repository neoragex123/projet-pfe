package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
public interface PointageRepository extends JpaRepository<Pointage, Long> {
    Optional<Pointage> findByUtilisateurAndDate(Utilisateur utilisateur, LocalDate date);
}
