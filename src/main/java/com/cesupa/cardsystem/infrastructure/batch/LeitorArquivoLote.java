package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroHeader;
import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroSolicitacao;
import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroTrailer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivoLote {

    public List<RegistroSolicitacao> ler(String caminho) {
        List<RegistroSolicitacao> registros = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            List<String> linhas = reader.lines()
                    .filter(l -> !l.isBlank())
                    .toList();

            if (linhas.isEmpty()) {
                throw new RuntimeException("Arquivo de lote vazio.");
            }

            String primeiraLinha = linhas.get(0);
            if (!primeiraLinha.startsWith("00")) {
                throw new RuntimeException("A primeira linha não é HEADER (00).");
            }

            RegistroHeader header = new RegistroHeader(
                    primeiraLinha.substring(0, 2).trim(),
                    primeiraLinha.substring(2, 10).trim(),
                    primeiraLinha.substring(10, 16).trim()
            );

            String ultimaLinha = linhas.get(linhas.size() - 1);
            if (!ultimaLinha.startsWith("99")) {
                throw new RuntimeException("A última linha não é TRAILER (99).");
            }

            RegistroTrailer trailer = new RegistroTrailer(
                    ultimaLinha.substring(0, 2).trim(),
                    ultimaLinha.substring(2, 10).trim(),
                    Integer.parseInt(ultimaLinha.substring(10, 18).trim())
            );

            if (!header.dataArquivo().equals(trailer.dataArquivo())) {
                throw new RuntimeException("Data do HEADER e do TRAILER não coincidem.");
            }

            int totalEsperado = trailer.totalRegistros();
            int totalEncontrado = linhas.size();
            if (totalEsperado != totalEncontrado) {
                throw new RuntimeException("Total de registros do trailer não confere com as linhas do arquivo.");
            }

            for (int i = 1; i < linhas.size() - 1; i++) {
                String linha = linhas.get(i);
                String tipo = linha.substring(0, 2);

                switch (tipo) {
                    case "01" -> {
                        if (linha.length() < 91) {
                            throw new RuntimeException("Linha " + (i + 1) + " inválida. Esperado no mínimo 91 caracteres.");
                        }

                        registros.add(new RegistroSolicitacao(
                                linha.substring(0, 2).trim(),
                                linha.substring(2, 13).trim(),
                                linha.substring(13, 53).trim(),
                                linha.substring(53, 61).trim(),
                                linha.substring(61, 71).trim(),
                                linha.substring(71, 81).trim(),
                                linha.substring(81, 91).trim()
                        ));
                    }

                    default -> throw new RuntimeException("Tipo de registro desconhecido na linha " + (i + 1) + ": " + tipo);
                }
            }

            return registros;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo de lote", e);
        }
    }
}
