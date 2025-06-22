package com.cesupa.cardsystem.infrastructure.batch.registros;

public record RegistroErro(
        String tipoRegistro,
        String dataSolicitacao,
        String idTransacao,
        String codigoErro,
        String descricaoErro
) {}
