package com.cesupa.cardsystem.domain.vo;

import com.cesupa.cardsystem.domain.exception.SenhaInvalidaException;

public record Senha(String valor) {

    public Senha {
        validar(valor);
    }

    private void validar(String valor) {
        if (valor == null || !valor.matches("\\d{6}")) {
            throw new SenhaInvalidaException("Senha deve ter exatamente 6 dígitos.");
        }
        if ("123456".equals(valor) || "654321".equals(valor)) {
            throw new SenhaInvalidaException("Senha não pode ser sequência.");
        }
        if (valor.chars().distinct().count() == 1) {
            throw new SenhaInvalidaException("Senha não pode ter todos os dígitos iguais.");
        }
    }
}
