package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.CancelarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.repository.LancamentoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.entity.Lancamento;

public class CancelarCartaoUseCase {

    private final CartaoRepository repository;
    private final LancamentoRepository lancamentoRepository;
    private final FaturaCartaoUseCase faturaCartaoUseCase;

    public CancelarCartaoUseCase(CartaoRepository repository,
                                 LancamentoRepository lancamentoRepository,
                                 FaturaCartaoUseCase faturaCartaoUseCase) {
        this.repository = repository;
        this.lancamentoRepository = lancamentoRepository;
        this.faturaCartaoUseCase = faturaCartaoUseCase;
    }

    public Cartao executar(CancelarCartaoEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());
        String motivo = entrada.motivo();

        Cartao cartao = repository.buscarPorNumero(entrada.numeroCartao())
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));

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
        return faturaCartaoUseCase.executar(cartao.getNumero())
                .status()
                .equalsIgnoreCase("EM_ABERTO");
    }
}
