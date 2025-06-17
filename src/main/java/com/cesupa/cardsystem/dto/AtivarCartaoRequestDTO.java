package com.cesupa.cardsystem.dto;

public record AtivarCartaoRequestDTO(
        String numero,
        String cpf,
        String senha
) {}
