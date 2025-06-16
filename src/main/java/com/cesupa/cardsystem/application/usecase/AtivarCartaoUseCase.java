package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.AtivarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.Senha;

public class AtivarCartaoUseCase {
    private final CartaoRepository repository;

    public AtivarCartaoUseCase(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao executar(AtivarCartaoEntrada entrada) {
       CPF cpf = new CPF(entrada.cpf());
       Senha senha = new Senha(entrada.senha());

       Cartao cartao = repository.buscarPorNumero(entrada.numero()).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
       cartao.ativar(cpf,senha);
       repository.salvar(cartao);
       return cartao;
    }
}
