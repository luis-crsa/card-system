package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.ComunicarPerdaRouboEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.enums.TipoDeOcorrencia;
import com.cesupa.cardsystem.domain.exception.CartaoNaoEncontradoException;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;

public class ComunicarPerdaRouboUseCase {

    private final CartaoRepository repository;

    public ComunicarPerdaRouboUseCase(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao executar(ComunicarPerdaRouboEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());
        var tipoDeOcorrencia = TipoDeOcorrencia.valueOf(entrada.tipoDeOcorrencia().toUpperCase());

        Cartao cartao = repository.buscarPorNumero(entrada.numero())
                .orElseThrow(CartaoNaoEncontradoException::new);
        cartao.comunicacaoPerdaRoubo(cpf, tipoDeOcorrencia);
        repository.salvar(cartao);
        return cartao;
    }
}
