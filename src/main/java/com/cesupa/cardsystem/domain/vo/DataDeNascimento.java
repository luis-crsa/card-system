package com.cesupa.cardsystem.domain.vo;

import java.time.LocalDate;
import java.time.Period;

public record DataDeNascimento(LocalDate data) {
    public DataDeNascimento {
        if (!maiorDeIdade(data)) {
            throw new IllegalArgumentException("Cliente deve ser maior de 18 anos.");
        }
    }

    private boolean maiorDeIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears() >= 18;
    }
}

