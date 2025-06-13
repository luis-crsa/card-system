package com.cesupa.cardsystem.domain.vo;

public record CPF(String valor) {

    public CPF {
        if (!isValid(valor)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }

    private boolean isValid(String cpf) {

        cpf = cpf.replaceAll("[^\\d]", "");

        if (cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\1{10}")) return false;

        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += (cpf.charAt(i) - '0') * (10 - i);
            }
            int digito1 = 11 - (soma % 11);
            if (digito1 >= 10) digito1 = 0;

            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += (cpf.charAt(i) - '0') * (11 - i);
            }
            int digito2 = 11 - (soma % 11);
            if (digito2 >= 10) digito2 = 0;

            return cpf.charAt(9) - '0' == digito1 && cpf.charAt(10) - '0' == digito2;

        } catch (Exception e) {
            return false;
        }
    }
}
