package com.example.ProjetGTP.services;

import com.example.ProjetGTP.dtos.PointageDto;
import com.example.ProjetGTP.entities.Pointage;
import com.example.ProjetGTP.entities.User;
import com.example.ProjetGTP.mappers.PointageMapper;
import com.example.ProjetGTP.repositories.PointageRepository;
import com.example.ProjetGTP.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointageService {
    private final PointageRepository pointageRepo;
    private final UserRepository userRepo;

    public PointageService(PointageRepository pointageRepo, UserRepository userRepo) {
        this.pointageRepo = pointageRepo;
        this.userRepo = userRepo;
    }

    public List<PointageDto> getAll() {
        return pointageRepo.findAll()
                .stream()
                .map(PointageMapper::toDto)
                .collect(Collectors.toList());
    }

    public PointageDto save(PointageDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        Pointage p = PointageMapper.toEntity(dto, user);
        return PointageMapper.toDto(pointageRepo.save(p));
    }
}
