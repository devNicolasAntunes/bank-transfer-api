package com.desafiosVagas.PicPay.service;

import com.desafiosVagas.PicPay.Mappers.Mapper;
import com.desafiosVagas.PicPay.controllerDTO.UserDTO;
import com.desafiosVagas.PicPay.exceptions.BusinessException;
import com.desafiosVagas.PicPay.exceptions.AcessDeniedException;
import com.desafiosVagas.PicPay.model.User;
import com.desafiosVagas.PicPay.model.UserType;
import com.desafiosVagas.PicPay.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final Mapper mapper;

    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User create(@Valid UserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (userRepository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }

        User user = mapper.toEntity(dto);

        return userRepository.save(user);
    }

    public User findById(UUID id, User authenticatedUser) {

        boolean isOwner = authenticatedUser.getId().equals(id);
        boolean isAdmin = authenticatedUser.getType() == UserType.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AcessDeniedException("Acesso negado");
        }

        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    public User delete(UUID id, User authenticatedUser) {

        boolean isOwner = authenticatedUser.getId().equals(id);
        boolean isAdmin = authenticatedUser.getType() == UserType.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AcessDeniedException("Acesso negado");
        }

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        userRepository.delete(existing);

        return existing;
    }

    public User update(UUID id, User user, User authenticatedUser) {

        boolean isOwner = authenticatedUser.getId().equals(id);
        boolean isAdmin = authenticatedUser.getType() == UserType.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AcessDeniedException("Acesso negado");
        }

        User existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (user.getEmail() != null &&
                !user.getEmail().equals(existing.getEmail()) &&
                userRepository.existsByEmail(user.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (user.getCpf() != null &&
                !user.getCpf().equals(existing.getCpf()) &&
                userRepository.existsByCpf(user.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }

        if (user.getName() != null) existing.setName(user.getName());
        if (user.getEmail() != null) existing.setEmail(user.getEmail());
        if (user.getCpf() != null) existing.setCpf(user.getCpf());
        if (user.getPassword() != null) existing.setPassword(user.getPassword());

        return userRepository.save(existing);
    }
}