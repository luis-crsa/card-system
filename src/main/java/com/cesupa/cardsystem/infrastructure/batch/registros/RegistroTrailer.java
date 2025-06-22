package com.cesupa.cardsystem.infrastructure.batch.registros;

public record RegistroTrailer(
        String tipoRegistro,
        String dataArquivo,
        int totalRegistros
) {}
