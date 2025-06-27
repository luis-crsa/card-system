package com.cesupa.cardsystem.domain.repository;

import com.cesupa.cardsystem.domain.entity.Lancamento;

import java.util.List;

public interface LancamentoRepository {
    List<Lancamento> buscarPorNumeroCartao(String numeroCartao);
}
