package com.desafiosVagas.PicPay.controller;


import com.desafiosVagas.PicPay.Mappers.Mapper;
import com.desafiosVagas.PicPay.controllerDTO.UserDTO;
import com.desafiosVagas.PicPay.model.User;
import com.desafiosVagas.PicPay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final UserService userService;

    private final Mapper mapper;

    public AdminController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> cadastrar(@RequestBody @Valid UserDTO dto) {
        User user = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(user));
    }

    @GetMapping("/{id}") //admin = any resource
    public ResponseEntity<UserDTO> obterPorId(@PathVariable UUID id, Authentication authentication) {
        User authenticated = (User) authentication.getPrincipal();

        User user = userService.findById(id, authenticated);

        return ResponseEntity.ok(mapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id, Authentication authentication) {
        User authenticated = (User) authentication.getPrincipal();

        userService.delete(id, authenticated);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> editar(@PathVariable UUID id, @RequestBody UserDTO dto, Authentication authentication) {
        User authenticated = (User) authentication.getPrincipal();

        User user = mapper.toEntity(dto);

        User uptaded = userService.update(id, user, authenticated);

        return ResponseEntity.ok(
                mapper.toDTO(uptaded)
        );
    }
}
