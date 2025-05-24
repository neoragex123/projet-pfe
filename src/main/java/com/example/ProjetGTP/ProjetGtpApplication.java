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
			if (!repo.existsByEmail("admin@gtp.com")) {
				Utilisateur admin = new Utilisateur();
				admin.setNom("Admin");
				admin.setPrenom("GTP");
				admin.setEmail("admin@gtp.com");
				admin.setMotDePasse(encoder.encode("admin123"));
				admin.setRole(Role.SUPER_ADMIN);
				admin.setChangementMotDePasse(false);
				admin.setDateEmbauche(LocalDate.of(2024, 1, 1));
				repo.save(admin);
			}
		};
	}
}
