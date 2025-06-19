package com.cesupa.cardsystem.application.usecase.dto;

public record BloquearCartaoEntrada(
        String numero,
        String cpf,
        String motivo
) {}
