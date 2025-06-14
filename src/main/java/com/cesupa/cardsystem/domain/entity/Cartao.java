package com.cesupa.cardsystem.domain.entity;

import com.cesupa.cardsystem.domain.enums.BandeiraCartao;
import com.cesupa.cardsystem.domain.enums.StatusCartao;
import com.cesupa.cardsystem.domain.enums.TipoCartao;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class Cartao {

    private UUID id;
    private CPF cpf;
    private String nomeCompleto;
    private DataDeNascimento dataNascimento;
    private RendaMensal rendaMensal;
    private TipoCartao tipo;
    private BandeiraCartao bandeira;
    private String numero;
    private StatusCartao status;


    public static Cartao solicitar(CPF cpf, String nomeCompleto, DataDeNascimento dataNascimento,
                            RendaMensal renda, TipoCartao tipo, BandeiraCartao bandeira){

        if (!renda.atendeMinimoPara(tipo)) {
            throw new IllegalArgumentException("Renda insuficiente para tipo de cartão solicitado.");
        }

        String numeroGerado = gerarNumero();

        return new Cartao(
                null,
                cpf,
                nomeCompleto,
                dataNascimento,
                renda,
                tipo,
                bandeira,
                numeroGerado,
                StatusCartao.SOLICITADO
        );
    }

    private static String gerarNumero() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
    }
}
