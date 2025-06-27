package com.cesupa.cardsystem.infrastructure.mapper;

import com.cesupa.cardsystem.domain.entity.Lancamento;
import com.cesupa.cardsystem.dto.LancamentoDTO;

public class LancamentoMapper {

    public static LancamentoDTO toDTO(Lancamento lancamento) {
        return new LancamentoDTO(
                lancamento.getData(),
                lancamento.getDescricao(),
                lancamento.getValor(),
                lancamento.isPago()
        );
    }
}
