package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;

public class BloquearCartaoUseCase {

    private final CartaoRepository cartaoRepository;

    public BloquearCartaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public void bloquear(BloquearCartaoEntrada entrada) {
        var cartaoOpt = cartaoRepository.buscarPorNumero(entrada.numeroCartao());

        if (cartaoOpt.isEmpty()) {
            throw new RuntimeException("Cartão não encontrado.");
        }

        var cartao = cartaoOpt.get();

        if (!cartao.getCpf().valor().equals(entrada.cpf())) {
            throw new RuntimeException("CPF não corresponde ao titular do cartão.");
        }

        cartao.bloquearTemporariamente(entrada.motivo());
        cartaoRepository.salvar(cartao);
    }
}
