package com.cesupa.cardsystem.dto;

import java.util.UUID;

public record CartaoMotivoResponseDTO(
        UUID id,
        String numero,
        String status,
        String motivo
) {}
