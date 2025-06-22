package com.cesupa.cardsystem.infrastructure.batch.registros;

import java.util.List;

public record RegistroCompleto(
        RegistroHeader header,
        List<RegistroSolicitacao> solicitacoes,
        List<RegistroBloqueio> bloqueios,
        List<RegistroCancelamento> cancelamentos,
        RegistroTrailer trailer
) {}
