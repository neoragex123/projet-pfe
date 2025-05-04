package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
@Repository
public interface UtilisateurRepository extends JpaRepository <Utilisateur, Long>{
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email);
}
