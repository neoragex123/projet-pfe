package com.example.ProjetGTP.controllers;

import com.example.ProjetGTP.dtos.UserDto;
import com.example.ProjetGTP.entities.User;
import com.example.ProjetGTP.mappers.UserMapper;
import com.example.ProjetGTP.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    // Injection via constructeur
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ GET - Récupérer tous les utilisateurs
    @GetMapping
    public List<UserDto> getUsers() {
        return userService.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    // ✅ POST - Créer un nouvel utilisateur
    @PostMapping
    public UserDto createUser(@RequestBody UserDto dto) {
        User saved = userService.save(UserMapper.toEntity(dto));
        return UserMapper.toDto(saved);
    }

    // ✅ PUT - Mettre à jour un utilisateur existant
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto dto) {
        User updated = userService.updateUser(id, dto);
        return UserMapper.toDto(updated);
    }

    // ✅ DELETE - Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
