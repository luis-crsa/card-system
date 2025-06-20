package com.cesupa.cardsystem.domain.vo;

import com.cesupa.cardsystem.domain.exception.IdadeInvalidaException;

import java.time.LocalDate;
import java.time.Period;

public record DataDeNascimento(LocalDate data) {
    public DataDeNascimento {
        if (!maiorDeIdade(data)) {
            throw new IdadeInvalidaException();
        }
    }

    private boolean maiorDeIdade(LocalDate nascimento) {
        return Period.between(nascimento, LocalDate.now()).getYears() >= 18;
    }
}
