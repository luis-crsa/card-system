package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.BloquearCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.CancelarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import com.cesupa.cardsystem.application.usecase.dto.CancelarCartaoEntrada;
import com.cesupa.cardsystem.application.usecase.dto.SolicitarCartaoEntrada;
import com.cesupa.cardsystem.domain.exception.CpfInvalidoException;
import com.cesupa.cardsystem.domain.exception.IdadeInvalidaException;
import com.cesupa.cardsystem.domain.exception.StatusInvalidoException;
import com.cesupa.cardsystem.infrastructure.batch.registros.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProcessadorArquivoLote {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;
    private final CancelarCartaoUseCase cancelarCartaoUseCase;

    public ProcessadorArquivoLote(SolicitarCartaoUseCase solicitarCartaoUseCase, BloquearCartaoUseCase bloquearCartaoUseCase, CancelarCartaoUseCase cancelarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
        this.cancelarCartaoUseCase = cancelarCartaoUseCase;
    }

    public List<RegistroErro> processar(RegistroCompleto lote) {
        List<RegistroErro> erros = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String dataSolicitacao = lote.header().dataArquivo();

        for (int i = 0; i < lote.solicitacoes().size(); i++) {
            RegistroSolicitacao registro = lote.solicitacoes().get(i);
            String idTransacao = String.format("%06d", i + 1);

            try {
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
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1202C",
                        "CPF inválido"
                ));
            } catch (IdadeInvalidaException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1503D",
                        "Idade menor que 18 anos"
                ));
            } catch (Exception e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "9999X",
                        "Erro desconhecido: " + e.getMessage()
                ));
            }
        }

        for (int i = 0; i < lote.bloqueios().size(); i++) {
            RegistroBloqueio registro = lote.bloqueios().get(i);
            String idTransacao = String.format("%06d", i + 1);

            try {
                var entrada = new BloquearCartaoEntrada(
                        registro.numero(),
                        registro.cpf(),
                        registro.motivo()
                );

                bloquearCartaoUseCase.executar(entrada);

            } catch (CpfInvalidoException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1202C",
                        "CPF inválido"
                ));
            } catch (IdadeInvalidaException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1503D",
                        "Idade menor que 18 anos"
                ));
            } catch (StatusInvalidoException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "0600B",
                        "Cartão não pode ser bloqueado"
                ));
            } catch (Exception e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "9999X",
                        "Erro desconhecido: " + e.getMessage()
                ));
            }
        }

        for (int i = 0; i < lote.cancelamentos().size(); i++) {
            RegistroCancelamento registro = lote.cancelamentos().get(i);
            String idTransacao = String.format("%06d", i + 1);

            try {
                var entrada = new CancelarCartaoEntrada(
                        registro.numero(),
                        registro.cpf(),
                        registro.motivo()
                );
                
                cancelarCartaoUseCase.executar(entrada);

            } catch (CpfInvalidoException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1202C",
                        "CPF inválido"
                ));
            } catch (IdadeInvalidaException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "1503D",
                        "Idade menor que 18 anos"
                ));
            } catch (StatusInvalidoException e) {
                erros.add(new RegistroErro(
                        registro.tipoRegistro(),
                        dataSolicitacao,
                        idTransacao,
                        "0600B",
                        "Cartão não pode ser cancelado"
                ));
            } catch (Exception e) {
                erros.add(new RegistroErro(
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
