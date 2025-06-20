package com.cesupa.cardsystem.domain.vo;

public record Senha(String valor) {


    public Senha {
        validar(valor);
    }

    private void validar(String valor) {
        if ("123456".equals(valor) || "654321".equals(valor)) {
            throw new IllegalArgumentException("Senha não pode ser sequência.");
        }
        if (valor.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("Senha não pode ter todos os dígitos iguais.");
        }
    }

}
