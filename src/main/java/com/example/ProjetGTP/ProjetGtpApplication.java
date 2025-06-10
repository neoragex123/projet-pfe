package com.example.ProjetGTP;


import com.example.ProjetGTP.entities.Role;


import com.example.ProjetGTP.entities.Utilisateur;
import com.example.ProjetGTP.repositories.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@SpringBootApplication
public class ProjetGtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetGtpApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UtilisateurRepository repo, PasswordEncoder encoder) {
		return args -> {

			// SUPER ADMIN
			if (!repo.existsByEmail("admin@gtp.com")) {
				Utilisateur admin = new Utilisateur();
				admin.setNom("Admin");
				admin.setPrenom("GTP");
				admin.setEmail("admin@gtp.com");
				admin.setMotDePasse(encoder.encode("1234"));
				admin.setRole(Role.SUPER_ADMIN);
				admin.setChangementMotDePasse(false);
				admin.setDateEmbauche(LocalDate.of(2024, 1, 1));
				repo.save(admin);
			}

			// EMPLOYÉ
			if (!repo.existsByEmail("employe@test.com")) {
				Utilisateur employe = new Utilisateur();
				employe.setNom("Test");
				employe.setPrenom("Employé");
				employe.setEmail("employe@test.com");
				employe.setMotDePasse(encoder.encode("1234"));
				employe.setRole(Role.EMPLOYE);
				employe.setChangementMotDePasse(false);
				employe.setDateEmbauche(LocalDate.of(2024, 1, 1));
				repo.save(employe);
			}

			// MANAGER
			if (!repo.existsByEmail("manager@test.com")) {
				Utilisateur manager = new Utilisateur();
				manager.setNom("Test");
				manager.setPrenom("Manager");
				manager.setEmail("manager@test.com");
				manager.setMotDePasse(encoder.encode("1234"));
				manager.setRole(Role.MANAGER);
				manager.setChangementMotDePasse(false);
				manager.setDateEmbauche(LocalDate.of(2024, 1, 1));
				repo.save(manager);
			}

			// RH
			if (!repo.existsByEmail("rh@test.com")) {
				Utilisateur rh = new Utilisateur();
				rh.setNom("Test");
				rh.setPrenom("RH");
				rh.setEmail("rh@test.com");
				rh.setMotDePasse(encoder.encode("1234"));
				rh.setRole(Role.RH);
				rh.setChangementMotDePasse(false);
				rh.setDateEmbauche(LocalDate.of(2024, 1, 1));
				repo.save(rh);
			}

			System.out.println("✅ Utilisateurs initiaux injectés avec succès.");
		};
	}
}
