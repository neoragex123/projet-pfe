package com.example.ProjetGTP.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomLogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.html", "/home.html", "/rh.html", "/h2-console/**", "/static/**", "/uploads/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // <- Création via Postman autorisée
                        .requestMatchers(HttpMethod.PATCH, "/api/conges/*/statut").hasAnyRole("RH", "MANAGER", "SUPER_ADMIN")
                        .requestMatchers("/api/conges/en-attente").hasAnyRole("RH", "MANAGER", "SUPER_ADMIN")
                        .requestMatchers("/ajout-user.html").hasAnyRole("RH", "SUPER_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users").hasAnyRole("RH", "SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/welcome", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessUrl("/login.html?logout")
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Pour H2 console

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
