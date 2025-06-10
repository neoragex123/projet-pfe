package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface CongeRepository extends JpaRepository<Conge, Long> {
    List<Conge> findByStatut(StatutConge statut);

    @Query("SELECT c FROM Conge c JOIN FETCH c.utilisateur WHERE c.statut = :statut")
    List<Conge> findByStatutWithUtilisateur(@Param("statut") StatutConge statut);

    List<Conge> findByUtilisateurEmailAndStatut(String email, StatutConge statut);

    List<Conge> findByUtilisateurEmail(String email);



}
