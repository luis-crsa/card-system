package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.exception.CartaoNaoEncontradoException;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;

public class AprovarCartaoUseCase {

    private final CartaoRepository repository;

    public AprovarCartaoUseCase(CartaoRepository repository) {
        this.repository = repository;
    }
    
    public String executar(String numero){
        Cartao cartao = repository.buscarPorNumero(numero)
                .orElseThrow(CartaoNaoEncontradoException::new);
        
        cartao.aprovar();
        repository.salvar(cartao);
        return "Cartão aprovado com sucesso";
    }
}
