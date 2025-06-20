package com.cesupa.cardsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SolitarCartaoRequestDTO(
        String cpf,
        String nomeCompleto,
        LocalDate dataNascimento,
        BigDecimal rendaMensal,
        String tipoCartao,
        String bandeiraCartao
) {}

