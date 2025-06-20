package com.cesupa.cardsystem.infrastructure.batch;

public record RegistroSolicitacaoLote(
        String tipoRegistro,
        String cpf,
        String nomeCompleto,
        String dataNascimento,
        String rendaMensal,
        String tipoCartao,
        String bandeiraCartao
) {}
