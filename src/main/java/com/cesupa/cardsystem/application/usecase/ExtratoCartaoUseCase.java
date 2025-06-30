package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.entity.Lancamento;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.repository.LancamentoRepository;

import java.util.List;

public class ExtratoCartaoUseCase {

    private final CartaoRepository cartaoRepository;
    private final LancamentoRepository lancamentoRepository;

    public ExtratoCartaoUseCase(CartaoRepository cartaoRepository, LancamentoRepository lancamentoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.lancamentoRepository = lancamentoRepository;
    }

    public List<Lancamento> executar(String numeroCartao) {
        Cartao cartao = cartaoRepository.buscarPorNumero(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));

        if (!cartao.getStatus().podeConsultarExtrato()) {
            throw new IllegalStateException("Cartão com status que impede a consulta.");
        }

        return lancamentoRepository.buscarPorNumeroCartao(numeroCartao);
    }
}
