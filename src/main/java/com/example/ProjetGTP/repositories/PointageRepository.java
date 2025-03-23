package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointageRepository extends JpaRepository<Pointage, Long> {
}
