package com.cesupa.cardsystem.infrastructure.batch.registros;

public record RegistroCancelamento(
        String tipoRegistro,
        String dataBloqueio,
        String numero,
        String cpf,
        String motivo
) {}
