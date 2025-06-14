package com.cesupa.cardsystem.dto;

import java.util.UUID;

public record CartaoResponseDTO(
        UUID id,
        String numero,
        String status,
        String tipoCartao,
        String bandeiraCartao
) {}

