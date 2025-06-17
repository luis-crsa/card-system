package com.cesupa.cardsystem.dto;

import java.util.UUID;

public record CartaoSenhaResponseDTO(
        UUID id,
        String numero,
        String status,
        String tipoCartao,
        String bandeiraCartao,
        String senha
) {}
