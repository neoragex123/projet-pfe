package com.example.ProjetGTP.repositories;

import com.example.ProjetGTP.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
