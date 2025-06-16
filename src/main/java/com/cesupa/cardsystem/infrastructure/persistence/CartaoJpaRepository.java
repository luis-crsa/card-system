package com.cesupa.cardsystem.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartaoJpaRepository extends JpaRepository<CartaoEntity, UUID> {
    Optional<CartaoEntity> findByNumero(String numero);
}
