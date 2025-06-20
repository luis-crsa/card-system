package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.SolicitarCartaoEntrada;
import com.cesupa.cardsystem.domain.exception.CpfInvalidoException;
import com.cesupa.cardsystem.domain.exception.IdadeInvalidaException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProcessarArquivoLote {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;

    public ProcessarArquivoLote(SolicitarCartaoUseCase solicitarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
    }

    public List<RegistroErroLote> processar(List<RegistroSolicitacaoLote> registros, String dataSolicitacao) {
        List<RegistroErroLote> erros = new ArrayList<>();

        for (int i = 0; i < registros.size(); i++) {
            RegistroSolicitacaoLote registro = registros.get(i);
            String idTransacao = String.format("%06d", i + 1);

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                
                var entrada = new SolicitarCartaoEntrada(
                        registro.cpf(),
                        registro.nomeCompleto(),
                        LocalDate.parse(registro.dataNascimento(), formatter),
                        new BigDecimal(registro.rendaMensal()),
                        registro.tipoCartao(),
                        registro.bandeiraCartao()
                );

                solicitarCartaoUseCase.executar(entrada);

            } catch (CpfInvalidoException e) {
                erros.add(new RegistroErroLote(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1202C",
                        "CPF inválido"
                ));
            } catch (IdadeInvalidaException e) {
                erros.add(new RegistroErroLote(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1503D",
                        "Idade menor que 18 anos"
                ));
            } catch (Exception e) {
                erros.add(new RegistroErroLote(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "9999X",
                        "Erro desconhecido: " + e.getMessage()
                ));
            }
        }

        return erros;
    }
}
