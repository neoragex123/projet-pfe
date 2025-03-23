package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.dtos.CongeDto;
import com.example.ProjetGTP.services.CongeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class CongeController {
    private final CongeService congeService;

    public CongeController(CongeService congeService) {
        this.congeService = congeService;
    }

    @GetMapping
    public List<CongeDto> getAll() {
        return congeService.getAll();
    }

    @PostMapping
    public CongeDto demander(@RequestBody CongeDto dto) {
        return congeService.demanderConge(dto);
    }

    @PutMapping("/{id}/valider")
    public CongeDto valider(@PathVariable Long id) {
        return congeService.validerConge(id);
    }

    @PutMapping("/{id}/refuser")
    public CongeDto refuser(@PathVariable Long id) {
        return congeService.refuserConge(id);
    }
}
