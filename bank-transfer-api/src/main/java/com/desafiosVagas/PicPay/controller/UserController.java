package com.desafiosVagas.PicPay.controller;

import com.desafiosVagas.PicPay.Mappers.Mapper;
import com.desafiosVagas.PicPay.adpter.CustomUserDetails;
import com.desafiosVagas.PicPay.controllerDTO.UserDTO;
import com.desafiosVagas.PicPay.model.User;
import com.desafiosVagas.PicPay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final Mapper mapper;

    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> cadastrar(@RequestBody @Valid UserDTO dto) {
        User user = userService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(user));
    }

    @GetMapping("/me") //user = own resource
    public ResponseEntity<UserDTO> obterPorId(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userService.findById(userDetails.getId(), userDetails.getUser());

        return ResponseEntity.ok(mapper.toDTO(user));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletar(Authentication authentication) {

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = userService.delete(userDetails.getId(), userDetails.getUser());

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{me}")
    public ResponseEntity<UserDTO> editar(Authentication authentication, @RequestBody UserDTO dto) {

        User authenticated = (User) authentication.getPrincipal();

        User user = mapper.toEntity(dto);

        User uptaded = userService.update(user.getId(), user, authenticated);

        return ResponseEntity.ok(mapper.toDTO(uptaded));
    }
}