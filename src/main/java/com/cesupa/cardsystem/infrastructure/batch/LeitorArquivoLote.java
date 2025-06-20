package com.cesupa.cardsystem.infrastructure.batch;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class LeitorArquivoLote {

    public List<RegistroSolicitacaoLote> ler(String caminho) {
        List<RegistroSolicitacaoLote> registros = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.isBlank()) continue;

                registros.add(new RegistroSolicitacaoLote(
                        linha.substring(0, 2),
                        linha.substring(2, 13),
                        linha.substring(13, 53).trim(),
                        linha.substring(53, 61),
                        linha.substring(61, 71),
                        linha.substring(71, 81).trim(),
                        linha.substring(81, 91).trim()
                ));
            }

            return registros;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo de lote", e);
        }
    }
}
