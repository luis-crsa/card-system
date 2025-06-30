package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.domain.entity.Lancamento;
import com.cesupa.cardsystem.domain.enums.TipoTransacao;
import com.cesupa.cardsystem.domain.repository.LancamentoRepository;
import com.cesupa.cardsystem.dto.FaturaDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class FaturaCartaoUseCase {

    private final LancamentoRepository lancamentoRepository;

    public FaturaCartaoUseCase(LancamentoRepository lancamentoRepository) {
        this.lancamentoRepository = lancamentoRepository;
    }

    public FaturaDTO executar(String numeroCartao) {
        List<Lancamento> lancamentos = lancamentoRepository.buscarPorNumeroCartao(numeroCartao);

        YearMonth atual = YearMonth.now();

        BigDecimal total = lancamentos.stream()
                .filter(l -> YearMonth.from(l.getData()).equals(atual))
                .filter(l -> l.getTipo() == TipoTransacao.DEBITO)
                .filter(l -> !l.isPago())
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDate vencimento = atual.plusMonths(1).atDay(10);

        String status = total.compareTo(BigDecimal.ZERO) > 0 ? "EM_ABERTO" : "FECHADA";

        return new FaturaDTO(total, vencimento, status);
    }
}

