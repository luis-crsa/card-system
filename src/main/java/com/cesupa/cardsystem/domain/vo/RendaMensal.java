package com.cesupa.cardsystem.domain.vo;

import com.cesupa.cardsystem.domain.enums.TipoCartao;

import java.math.BigDecimal;

public record RendaMensal(BigDecimal valor) {
    public RendaMensal {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Renda mensal deve ser positiva.");
        }
    }

    public boolean atendeMinimoPara(TipoCartao tipo) {
        return switch (tipo) {
            case CREDITO -> valor.compareTo(BigDecimal.valueOf(1000)) >= 0;
            case DEBITO -> valor.compareTo(BigDecimal.valueOf(100)) >= 0;
        };
    }
}

