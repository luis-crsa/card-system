package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.Senha;

public class AtivarCartaoUseCase {
    private final CartaoRepository cartaoRepository;

    public AtivarCartaoUseCase(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public void executar(String numeroCartao, String cpf, String senha) {
        Cartao cartao = cartaoRepository.buscarPorNumero(numeroCartao)
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));

        cartao.ativar(new CPF(cpf), new Senha(senha));

        cartaoRepository.salvar(cartao);
    }
}
