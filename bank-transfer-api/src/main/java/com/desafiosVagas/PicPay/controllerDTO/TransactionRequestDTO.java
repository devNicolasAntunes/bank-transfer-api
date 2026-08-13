package com.desafiosVagas.PicPay.controllerDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class TransactionRequestDTO {

    @NotNull
    private UUID senderId;

    @NotNull
    private UUID receiverId;

    @NotNull
    @Positive
    private BigDecimal amount;
}
