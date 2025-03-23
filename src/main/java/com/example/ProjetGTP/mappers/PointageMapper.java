package com.example.ProjetGTP.mappers;

import com.example.ProjetGTP.dtos.PointageDto;
import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.User;

public class PointageMapper {
    public static PointageDto toDto(Pointage p) {
        PointageDto dto = new PointageDto();
        dto.setId(p.getId());
        dto.setDateTime(p.getDateTime());
        dto.setType(p.getType());
        dto.setUserId(p.getUser().getId());
        return dto;
    }

    public static Pointage toEntity(PointageDto dto, User user) {
        Pointage p = new Pointage();
        p.setId(dto.getId());
        p.setDateTime(dto.getDateTime());
        p.setType(dto.getType());
        p.setUser(user);
        return p;
    }
}
