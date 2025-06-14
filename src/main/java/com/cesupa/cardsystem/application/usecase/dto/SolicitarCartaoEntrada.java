package com.cesupa.cardsystem.application.usecase.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SolicitarCartaoEntrada(
        String cpf,
        String nomeCompleto,
        LocalDate dataNascimento,
        BigDecimal rendaMensal,
        String tipoCartao,
        String bandeiraCartao
) {}
