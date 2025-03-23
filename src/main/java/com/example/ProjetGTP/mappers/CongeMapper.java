package com.example.ProjetGTP.mappers;

import com.example.ProjetGTP.dtos.CongeDto;
import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.entities.User;

public class CongeMapper {
    public static CongeDto toDto(Conge conge) {
        CongeDto dto = new CongeDto();
        dto.setId(conge.getId());
        dto.setDateDebut(conge.getDateDebut());
        dto.setDateFin(conge.getDateFin());
        dto.setType(conge.getType());
        dto.setStatut(conge.getStatut().name());
        dto.setUserId(conge.getUser().getId());
        return dto;
    }

    public static Conge toEntity(CongeDto dto, User user) {
        Conge c = new Conge();
        c.setId(dto.getId());
        c.setDateDebut(dto.getDateDebut());
        c.setDateFin(dto.getDateFin());
        c.setType(dto.getType());
        c.setStatut(StatutConge.valueOf(dto.getStatut()));
        c.setUser(user);
        return c;
    }
}
