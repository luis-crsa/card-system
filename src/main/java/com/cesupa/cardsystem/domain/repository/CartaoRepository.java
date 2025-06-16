package com.cesupa.cardsystem.domain.repository;

import com.cesupa.cardsystem.domain.entity.Cartao;

import java.util.Optional;
import java.util.UUID;

public interface CartaoRepository {
    void salvar(Cartao cartao);
    Optional<Cartao> buscarPorId(UUID id);
    Optional<Cartao> buscarPorNumero(String numero);
}
