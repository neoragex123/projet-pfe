package com.example.ProjetGTP.mappers;

import com.example.ProjetGTP.dtos.UserDto;
import com.example.ProjetGTP.entities.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setMotDePasse(user.getMotDePasse());
        dto.setRole(user.getRole());
        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setNom(dto.getNom());
        user.setMotDePasse(dto.getMotDePasse());
        user.setRole(dto.getRole());
        return user;
    }
}
