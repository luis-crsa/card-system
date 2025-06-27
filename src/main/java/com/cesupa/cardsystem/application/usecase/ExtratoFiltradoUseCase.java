package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.domain.entity.Lancamento;
import com.cesupa.cardsystem.domain.enums.TipoTransacao;
import com.cesupa.cardsystem.domain.repository.LancamentoRepository;
import com.cesupa.cardsystem.dto.ExtratoFiltradoDTO;
import com.cesupa.cardsystem.infrastructure.mapper.LancamentoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExtratoFiltradoUseCase {

    private final LancamentoRepository lancamentoRepository;

    public ExtratoFiltradoUseCase(LancamentoRepository repo) {
        this.lancamentoRepository = repo;
    }

    public ExtratoFiltradoDTO executar(String numeroCartao, LocalDate inicio, LocalDate fim,
                                       TipoTransacao tipo, BigDecimal valorMin, BigDecimal valorMax) {
        List<Lancamento> lancamentos = lancamentoRepository.buscarPorNumeroCartao(numeroCartao);

        var filtrados = lancamentos.stream()
                .filter(l -> (inicio == null || !l.getData().isBefore(inicio)))
                .filter(l -> (fim == null || !l.getData().isAfter(fim)))
                .filter(l -> (tipo == null || l.getTipo().equals(tipo)))
                .filter(l -> (valorMin == null || l.getValor().compareTo(valorMin) >= 0))
                .filter(l -> (valorMax == null || l.getValor().compareTo(valorMax) <= 0))
                .toList();

        BigDecimal totalDebito = filtrados.stream()
                .filter(l -> l.getTipo().equals(TipoTransacao.DEBITO))
                .map(Lancamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal limite = new BigDecimal("5000"); // valor fictício
        BigDecimal saldo = limite.subtract(totalDebito);

        return new ExtratoFiltradoDTO(
                filtrados.stream().map(LancamentoMapper::toDTO).toList(),
                saldo
        );
    }
}

