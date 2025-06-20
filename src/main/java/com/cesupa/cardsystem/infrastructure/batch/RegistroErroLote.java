package com.cesupa.cardsystem.infrastructure.batch;

public record RegistroErroLote(
        String tipoRegistro,
        String data,
        String idTransacao,
        String codigoErro,
        String descricaoErro
) {}

