package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RodarAplicacaoLote  implements  CommandLineRunner{
    private final SolicitarCartaoUseCase solicitarCartaoUseCase;

    public RodarAplicacaoLote(SolicitarCartaoUseCase solicitarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
    }

    @Override
    public void run(String... args) {
        System.out.println("🟢 Iniciando processamento em lote...");

        if (args.length > 0 && args[0].endsWith(".txt")) {
            var reader = new LeitorArquivoLote();
            var processor = new ProcessarArquivoLote(solicitarCartaoUseCase);
            var registros = reader.ler(args[0]);
            processor.processar(registros);
        }else {
            System.out.println("ℹ️ Nenhum arquivo .txt informado. API rodando normalmente.");
        }
    }
}
