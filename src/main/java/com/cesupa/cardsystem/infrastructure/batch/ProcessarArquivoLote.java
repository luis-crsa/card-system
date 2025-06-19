package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.SolicitarCartaoEntrada;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProcessarArquivoLote {
    private final SolicitarCartaoUseCase solicitarCartaoUseCase;

    public ProcessarArquivoLote(SolicitarCartaoUseCase solicitarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
    }

    public void processar(List<RegistroLote> registros) {
        for (RegistroLote registro : registros) {
            try {
                var entrada = new SolicitarCartaoEntrada(
                        registro.cpf(),
                        registro.nomeCompleto(),
                        LocalDate.parse(registro.dataNascimento()),
                        new BigDecimal(registro.rendaMensal()),
                        registro.tipoCartao(),
                        registro.bandeiraCartao()
                );
                solicitarCartaoUseCase.executar(entrada);
                System.out.println("Solicitação criada com sucesso para CPF: " + registro.cpf());
            } catch (Exception e) {
                System.out.println("Erro no CPF " + registro.cpf() + ": " + e.getMessage());
            }
        }
    }
}
