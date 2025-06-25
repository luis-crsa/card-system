package com.cesupa.cardsystem.dto;

import java.util.UUID;

public record CartaoTipoOcorrenciaResponseDTO(
        UUID id,
        String numero,
        String status,
        String tipoDeOcorrenia
) {}
