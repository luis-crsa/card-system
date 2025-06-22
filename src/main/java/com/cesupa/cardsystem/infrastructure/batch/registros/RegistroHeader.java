package com.cesupa.cardsystem.infrastructure.batch.registros;

public record RegistroHeader(
        String tipoRegistro,
        String dataArquivo,
        String codigoRemetente
) {}
