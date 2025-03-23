package com.example.ProjetGTP.services;

import com.example.ProjetGTP.dtos.CongeDto;
import com.example.ProjetGTP.entities.Conge;
import com.example.ProjetGTP.entities.StatutConge;
import com.example.ProjetGTP.entities.User;
import com.example.ProjetGTP.mappers.CongeMapper;
import com.example.ProjetGTP.repositories.CongeRepository;
import com.example.ProjetGTP.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CongeService {
    private final CongeRepository congeRepo;
    private final UserRepository userRepo;

    public CongeService(CongeRepository congeRepo, UserRepository userRepo) {
        this.congeRepo = congeRepo;
        this.userRepo = userRepo;
    }

    public List<CongeDto> getAll() {
        return congeRepo.findAll().stream()
                .map(CongeMapper::toDto)
                .collect(Collectors.toList());
    }

    public CongeDto demanderConge(CongeDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        dto.setStatut("EN_ATTENTE");
        Conge conge = CongeMapper.toEntity(dto, user);
        return CongeMapper.toDto(congeRepo.save(conge));
    }

    public CongeDto validerConge(Long id) {
        Conge conge = congeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));

        conge.setStatut(StatutConge.VALIDE);
        return CongeMapper.toDto(congeRepo.save(conge));
    }

    public CongeDto refuserConge(Long id) {
        Conge conge = congeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Congé introuvable"));

        conge.setStatut(StatutConge.REFUSE);
        return CongeMapper.toDto(congeRepo.save(conge));
    }
}
