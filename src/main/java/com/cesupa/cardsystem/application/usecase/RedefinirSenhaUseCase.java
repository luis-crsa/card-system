package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.RedefinirSenhaEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.Senha;

public class RedefinirSenhaUseCase {
    private final CartaoRepository repository;

    public RedefinirSenhaUseCase(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao executar(RedefinirSenhaEntrada input){
        CPF cpf = new CPF(input.cpf());
        Senha senhaAntiga = new Senha(input.senhaAntiga());
        Senha senhaNova = new Senha(input.senhaNova());

        Cartao cartao = repository.buscarPorNumero(input.numero()).orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));
        cartao.redefinirSenha(cpf, senhaAntiga, senhaNova);
        repository.salvar(cartao);
        return cartao;
    }
}
