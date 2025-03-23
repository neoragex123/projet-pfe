package com.example.ProjetGTP.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String nom;
    private String motDePasse;
    private String role;
}
