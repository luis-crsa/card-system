package com.cesupa.cardsystem.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface LancamentoJpaRepository extends JpaRepository<LancamentoEntity, UUID> {
    List<LancamentoEntity> findByNumeroCartao(String numeroCartao);
    List<LancamentoEntity> findByNumeroCartaoAndPagoFalse(String numeroCartao);
}
