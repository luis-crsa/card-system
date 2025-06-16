package com.cesupa.cardsystem.application.usecase.dto;

public record BloquearCartaoEntrada(
        String numeroCartao,
        String cpf,
        String motivo
) {}
