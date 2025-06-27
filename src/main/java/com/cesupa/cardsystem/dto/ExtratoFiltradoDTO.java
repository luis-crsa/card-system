package com.cesupa.cardsystem.dto;

import java.math.BigDecimal;
import java.util.List;

public record ExtratoFiltradoDTO(
        List<LancamentoDTO> transacoes,
        BigDecimal saldoDisponivel
) {}
