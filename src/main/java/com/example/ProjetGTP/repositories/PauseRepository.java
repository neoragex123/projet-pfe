package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.Pause;
import com.example.ProjetGTP.entities.Pointage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface PauseRepository extends JpaRepository<Pause, Long> {
    List<Pause> findByPointage(Pointage pointage);

}
