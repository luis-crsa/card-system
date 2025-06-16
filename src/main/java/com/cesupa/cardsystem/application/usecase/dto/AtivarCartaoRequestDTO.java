package com.cesupa.cardsystem.application.usecase.dto;

public record AtivarCartaoRequestDTO(
        String numero,
        String cpf,
        String senha
) {}
