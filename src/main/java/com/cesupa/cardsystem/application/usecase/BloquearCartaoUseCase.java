package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.exception.CartaoNaoEncontradoException;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;

public class BloquearCartaoUseCase {

    private final CartaoRepository repository;

    public BloquearCartaoUseCase(CartaoRepository cartaoRepository) {
        this.repository = cartaoRepository;
    }

    public Cartao executar(BloquearCartaoEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());
        String motivo = entrada.motivo();

        Cartao cartao = repository.buscarPorNumero(entrada.numero())
                .orElseThrow(CartaoNaoEncontradoException::new);
        cartao.bloquearTemporariamente(cpf, motivo);
        repository.salvar(cartao);
        return cartao;
    }
}
