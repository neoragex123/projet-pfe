package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.ActionHistorique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActionHistoriqueRepository extends JpaRepository<ActionHistorique, Long> {
    List<ActionHistorique> findByUtilisateurEmailOrderByDateActionDesc(String email);
}
