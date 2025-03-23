package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.dtos.PointageDto;
import com.example.ProjetGTP.services.PointageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pointages")
public class PointageController {
    private final PointageService pointageService;

    public PointageController(PointageService pointageService) {
        this.pointageService = pointageService;
    }

    @GetMapping
    public List<PointageDto> getAll() {
        return pointageService.getAll();
    }

    @PostMapping
    public PointageDto save(@RequestBody PointageDto dto) {
        return pointageService.save(dto);
    }
}
