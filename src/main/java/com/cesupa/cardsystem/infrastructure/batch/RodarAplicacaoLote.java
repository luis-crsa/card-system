package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RodarAplicacaoLote implements CommandLineRunner {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;

    public RodarAplicacaoLote(SolicitarCartaoUseCase solicitarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
    }

    @Override
    public void run(String... args) {
        
        if (args.length > 0 && args[0].toUpperCase().endsWith(".IN")) {
            System.out.println("Iniciando processamento em lote...");
            
            String caminhoArquivo = args[0];
            String nomeArquivo = caminhoArquivo.substring(caminhoArquivo.lastIndexOf("/") + 1); // ou '\\' no Windows
            String dataSolicitacao = nomeArquivo.substring(4, 12); // CARD20230619001.IN → "20230619"

            var leitor = new LeitorArquivoLote();
            var registros = leitor.ler(caminhoArquivo);

            var processador = new ProcessarArquivoLote(solicitarCartaoUseCase);
            List<RegistroErroLote> erros = processador.processar(registros, dataSolicitacao);

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
