package com.cesupa.cardsystem.infrastructure.batch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GravadorArquivoErro {

    public void gravar(List<RegistroErroLote> erros, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (RegistroErroLote erro : erros) {
                String linha = formatarLinhaErro(erro);
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar o arquivo .ERR", e);
        }
    }

    private String formatarLinhaErro(RegistroErroLote erro) {
        return String.format(
                "%-2s%-8s%-6s%-10s%-100s",
                erro.tipoRegistro(),
                erro.dataSolicitacao(),
                erro.idTransacao(),
                erro.codigoErro(),
                erro.descricaoErro()
        );
    }
}
