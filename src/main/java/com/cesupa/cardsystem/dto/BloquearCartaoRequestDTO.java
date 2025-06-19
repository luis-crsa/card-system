package com.cesupa.cardsystem.dto;

public record BloquearCartaoRequestDTO(
        String numero,
        String cpf,
        String motivo
) {}
