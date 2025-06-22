package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.infrastructure.batch.registros.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LeitorArquivoLote {

    public RegistroCompleto ler(String caminho) {
        List<RegistroSolicitacao> solicitacoes = new ArrayList<>();
        List<RegistroBloqueio> bloqueios = new ArrayList<>();
        List<RegistroCancelamento> cancelamentos = new ArrayList<>();

        RegistroHeader header = null;
        RegistroTrailer trailer = null;

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

            header = new RegistroHeader(
                    primeiraLinha.substring(0, 2).trim(),
                    primeiraLinha.substring(2, 10).trim(),
                    primeiraLinha.substring(10, 16).trim()
            );

            String ultimaLinha = linhas.get(linhas.size() - 1);
            if (!ultimaLinha.startsWith("99")) {
                throw new RuntimeException("A última linha não é TRAILER (99).");
            }

            trailer = new RegistroTrailer(
                    ultimaLinha.substring(0, 2).trim(),
                    ultimaLinha.substring(2, 10).trim(),
                    Integer.parseInt(ultimaLinha.substring(10, 18).trim())
            );

            if (!header.dataArquivo().equals(trailer.dataArquivo())) {
                throw new RuntimeException("Data do HEADER e do TRAILER não coincidem.");
            }

            if (linhas.size() != trailer.totalRegistros()) {
                throw new RuntimeException("Total de registros do TRAILER difere do total real do arquivo.");
            }

            for (int i = 1; i < linhas.size() - 1; i++) {
                String linha = linhas.get(i);
                String tipo = linha.substring(0, 2);

                switch (tipo) {
                    case "01" -> solicitacoes.add(new RegistroSolicitacao(
                            linha.substring(0, 2),
                            linha.substring(2, 10),
                            linha.substring(10, 21),
                            linha.substring(21, 61).trim(),
                            linha.substring(61, 69),
                            linha.substring(69, 79),
                            linha.substring(79, 89).trim(),
                            linha.substring(89, 99).trim()
                    ));

                    case "02" -> bloqueios.add(new RegistroBloqueio(
                            linha.substring(0, 2),
                            linha.substring(2, 10),
                            linha.substring(10, 26),
                            linha.substring(26, 37),
                            linha.substring(37, 97).trim()
                    ));

                    case "03" -> cancelamentos.add(new RegistroCancelamento(
                            linha.substring(0, 2),
                            linha.substring(2, 10),
                            linha.substring(10, 26),
                            linha.substring(26, 37),
                            linha.substring(37, 97).trim()
                    ));

                    default -> throw new RuntimeException("Tipo de registro desconhecido na linha " + (i + 1) + ": " + tipo);
                }
            }

            return new RegistroCompleto(header, solicitacoes, bloqueios, cancelamentos, trailer);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo de lote", e);
        }
    }
}
