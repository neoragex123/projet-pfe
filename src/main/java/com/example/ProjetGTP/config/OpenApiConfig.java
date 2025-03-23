package com.example.ProjetGTP.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI gtpOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GTP - API de Gestion du Temps et des Présences")
                        .version("1.0")
                        .description("Documentation des endpoints de l'application GTP"));
    }
}
