package com.cesupa.cardsystem.application.usecase.dto;

public record AtivarCartaoEntrada(
        String numero,
        String cpf,
        String senha
) {}
