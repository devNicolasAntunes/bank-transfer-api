package com.desafiosVagas.PicPay.repository;

import com.desafiosVagas.PicPay.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByName(String name);

    Optional<User> findByEmail(String gmail);

    Optional<User> findByCpf(String cpf);

    boolean existsByEmail(String gmail);
    boolean existsByCpf(String cpf);
    User save(User user);
}
