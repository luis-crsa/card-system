package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.CancelarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.exception.CartaoNaoEncontradoException;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;

public class CancelarCartaoUseCase {

    private final CartaoRepository repository;

    public CancelarCartaoUseCase(CartaoRepository cartaoRepository) {
        this.repository = cartaoRepository;
    }

    public Cartao executar(CancelarCartaoEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());
        String motivo = entrada.motivo();

        Cartao cartao = repository.buscarPorNumero(entrada.numeroCartao())
                .orElseThrow(CartaoNaoEncontradoException::new);

        if (!cartao.getCpf().valor().equals(cpf.valor())) {
            throw new IllegalArgumentException("CPF não corresponde ao titular do cartão.");
        }

        if (temFaturaEmAberto(cartao)) {
            throw new IllegalArgumentException("Cartão não pode ser cancelado pois há fatura em aberto.");
        }

        cartao.cancelarDefinitivamente(motivo);
        repository.salvar(cartao);
        return cartao;
    }

    private boolean temFaturaEmAberto(Cartao cartao) {
        return false;
    }
}
