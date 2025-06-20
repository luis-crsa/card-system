package com.cesupa.cardsystem.infrastructure.batch;

public record RegistroErroLote(
        String tipoRegistro,
        String dataSolicitacao,
        String idTransacao,
        String codigoErro,
        String descricaoErro
) {}
