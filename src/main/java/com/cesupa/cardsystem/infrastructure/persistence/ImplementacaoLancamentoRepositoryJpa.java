package com.cesupa.cardsystem.infrastructure.persistence;

import com.cesupa.cardsystem.domain.entity.Lancamento;
import com.cesupa.cardsystem.domain.repository.LancamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImplementacaoLancamentoRepositoryJpa implements LancamentoRepository {

    private final LancamentoJpaRepository jpaRepository;

    public ImplementacaoLancamentoRepositoryJpa(LancamentoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Lancamento> buscarPorNumeroCartao(String numeroCartao) {
        return jpaRepository.findByNumeroCartao(numeroCartao).stream()
                .map(entity -> new Lancamento(entity.getData(), entity.getDescricao(), entity.getValor()))
                .toList();
    }
}
