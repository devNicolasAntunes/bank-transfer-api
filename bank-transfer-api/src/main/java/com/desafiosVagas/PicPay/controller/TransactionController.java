package com.desafiosVagas.PicPay.controller;


import com.desafiosVagas.PicPay.controllerDTO.TransactionRequestDTO;
import com.desafiosVagas.PicPay.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transferService) {
        this.transactionService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@Valid @RequestBody TransactionRequestDTO request) {

        transactionService.transfer(
                request.getSenderId(),
                request.getReceiverId(),
                request.getAmount()
        );

        return ResponseEntity.noContent().build();
    }
}
