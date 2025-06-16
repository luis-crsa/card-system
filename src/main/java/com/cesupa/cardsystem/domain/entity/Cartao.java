package com.cesupa.cardsystem.domain.entity;

import com.cesupa.cardsystem.domain.enums.BandeiraCartao;
import com.cesupa.cardsystem.domain.enums.StatusCartao;
import com.cesupa.cardsystem.domain.enums.TipoCartao;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;

import java.util.UUID;

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
    private String motivoBloqueio;

    public Cartao(UUID id, CPF cpf, String nomeCompleto, DataDeNascimento dataNascimento,
                  RendaMensal rendaMensal, TipoCartao tipo, BandeiraCartao bandeira,
                  String numero, StatusCartao status, String motivoBloqueio) {

        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.tipo = tipo;
        this.bandeira = bandeira;
        this.numero = numero;
        this.status = status;
        this.motivoBloqueio = motivoBloqueio;
    }

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
                StatusCartao.SOLICITADO,
                null
        );
    }

    public void bloquearTemporariamente(String motivo) {
        if (this.status != StatusCartao.ATIVO) {
            throw new RuntimeException("Somente cartões ativos podem ser bloqueados temporariamente");
        }
        this.status = StatusCartao.BLOQUEADO_TEMPORARIO;
        this.motivoBloqueio = motivo;
    }

    private static String gerarNumero() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public CPF getCpf() {
        return cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public DataDeNascimento getDataNascimento() {
        return dataNascimento;
    }

    public RendaMensal getRendaMensal() {
        return rendaMensal;
    }

    public TipoCartao getTipo() {
        return tipo;
    }

    public BandeiraCartao getBandeira() {
        return bandeira;
    }

    public String getNumero() {
        return numero;
    }

    public StatusCartao getStatus() {
        return status;
    }

    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }

    public void atribuirId(UUID id) {
        this.id = id;
    }
}