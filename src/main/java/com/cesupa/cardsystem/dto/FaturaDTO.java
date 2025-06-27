package com.cesupa.cardsystem.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaDTO(
        BigDecimal valorAtual,
        LocalDate vencimento,
        String status
) {}
