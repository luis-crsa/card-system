package com.cesupa.cardsystem.dto;

import java.time.LocalDate;
import java.math.BigDecimal;

public record LancamentoDTO(LocalDate data, String descricao, BigDecimal valor, boolean pago) {}


