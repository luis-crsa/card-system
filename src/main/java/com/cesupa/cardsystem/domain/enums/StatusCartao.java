package com.cesupa.cardsystem.domain.enums;

public enum StatusCartao {
    SOLICITADO,
    APROVADO,
    ATIVO,
    BLOQUEADO_TEMPORARIO,
    CANCELADO,
    BLOQUEADO_PERDA_ROUBO;

    public boolean podeConsultarExtrato() {
        return this == ATIVO || this == BLOQUEADO_TEMPORARIO;
    }
}
