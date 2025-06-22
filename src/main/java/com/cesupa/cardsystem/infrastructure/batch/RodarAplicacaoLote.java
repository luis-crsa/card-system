package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.BloquearCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.CancelarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroErro;
import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroCompleto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RodarAplicacaoLote implements CommandLineRunner {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;
    private final CancelarCartaoUseCase cancelarCartaoUseCase;
    
    public RodarAplicacaoLote(SolicitarCartaoUseCase solicitarCartaoUseCase, BloquearCartaoUseCase bloquearCartaoUseCase, CancelarCartaoUseCase cancelarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
        this.cancelarCartaoUseCase = cancelarCartaoUseCase;
    }

    @Override
    public void run(String... args) {

        if (args.length > 0 && args[0].toUpperCase().endsWith(".IN")) {
            System.out.println("Iniciando processamento em lote...");

            String caminhoArquivo = args[0];

            LeitorArquivoLote leitor = new LeitorArquivoLote();
            RegistroCompleto registros = leitor.ler(caminhoArquivo);

            ProcessadorArquivoLote processador = new ProcessadorArquivoLote(solicitarCartaoUseCase, bloquearCartaoUseCase, cancelarCartaoUseCase);
            List<RegistroErro> erros = processador.processar(registros);

            if (!erros.isEmpty()) {
                String caminhoErr = caminhoArquivo.replace(".IN", ".ERR");
                new GravadorArquivoErro().gravar(erros, caminhoErr);
                System.out.println("Arquivo de erro gerado: " + caminhoErr);
            } else {
                System.out.println("Processamento concluído sem erros.");
            }

        } else {
            System.out.println("Nenhum arquivo .IN informado. API rodando normalmente.");
        }
    }
}
