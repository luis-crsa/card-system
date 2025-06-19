package com.cesupa.cardsystem.infrastructure.batch;

public record RegistroLote(
        String cpf,
        String nomeCompleto,
        String dataNascimento,
        String rendaMensal,
        String tipoCartao,
        String bandeiraCartao
) {}
