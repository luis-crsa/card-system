package com.cesupa.cardsystem.infrastructure.batch;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class LeitorArquivoLote {

    public List<RegistroLote> ler(String caminhoArquivo) {
        List<RegistroLote> registros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(";");
                if (campos.length == 6) {
                    registros.add(new RegistroLote(
                            campos[0], campos[1], campos[2], campos[3], campos[4], campos[5]));
                }else{
                    throw new IllegalArgumentException("Número de campos inválido. Esperado 6, encontrado " + campos.length);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo de lote", e);
        }

        return registros;
    }
}
