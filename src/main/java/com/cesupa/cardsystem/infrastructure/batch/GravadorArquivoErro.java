package com.cesupa.cardsystem.infrastructure.batch;

import com.cesupa.cardsystem.infrastructure.batch.registros.RegistroErro;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GravadorArquivoErro {

    public void gravar(List<RegistroErro> erros, String caminhoArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (RegistroErro erro : erros) {
                String linha = formatarLinhaErro(erro);
                writer.write(linha);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar o arquivo .ERR", e);
        }
    }

    private String formatarLinhaErro(RegistroErro erro) {
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
