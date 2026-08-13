package com.desafiosVagas.PicPay.service;

import com.desafiosVagas.PicPay.exceptions.BusinessException;
import com.desafiosVagas.PicPay.model.User;
import com.desafiosVagas.PicPay.model.UserType;
import com.desafiosVagas.PicPay.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransactionService {

    private final UserRepository userRepository;

    public TransactionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void transfer(UUID senderId, UUID receiverId, BigDecimal amount) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new BusinessException("Remetente não encontrado"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new BusinessException("Destinatário não encontrado"));

        if(senderId.equals(receiverId)) {
            throw new BusinessException("Transferência para a mesma conta não é permitida");
        }

        if (sender.getType() == UserType.ADMIN) {
            throw new BusinessException("Lojistas não podem realizar transferências");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        userRepository.save(sender);
        userRepository.save(receiver);
    }
}
