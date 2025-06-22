package com.cesupa.cardsystem.infrastructure.batch.registros;

public record RegistroSolicitacao(
        String tipoRegistro,
        String dataSolicitacao,
        String cpf,
        String nomeCompleto,
        String dataNascimento,
        String rendaMensal,
        String tipoCartao,
        String bandeiraCartao
) {}
