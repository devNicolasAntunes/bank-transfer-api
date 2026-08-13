package com.desafiosVagas.PicPay.controllerDTO;

import com.desafiosVagas.PicPay.model.UserType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "name",
        "cpf",
        "email",
        "balance",
        "type",
        "password"
})

@Getter
@Setter
public class UserDTO {

    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(
            regexp = "\\d{11}",
            message = "CPF deve conter 11 dígitos numéricos"
    )
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Senha deve conter letras maiúsculas, minúsculas, números e símbolos"
    )
    private String password;

    private UserType type;

    @Column(name = "balance", precision = 10, scale = 2)
    private BigDecimal balance;


}
