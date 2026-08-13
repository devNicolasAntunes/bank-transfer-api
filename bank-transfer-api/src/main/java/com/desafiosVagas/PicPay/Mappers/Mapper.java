package com.desafiosVagas.PicPay.Mappers;

import com.desafiosVagas.PicPay.controllerDTO.UserDTO;
import com.desafiosVagas.PicPay.model.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public User toEntity(UserDTO dto) {
        User user = new User();

        user.setName(dto.getName());
        user.setCpf(dto.getCpf());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setType(dto.getType());
        user.setBalance(dto.getBalance());

        return user;
    }

    public UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setCpf(user.getCpf());
        dto.setEmail(user.getEmail());
        dto.setType(user.getType());
        dto.setBalance(user.getBalance());

        return dto;
    }
}
