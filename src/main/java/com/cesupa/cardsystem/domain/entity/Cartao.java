package com.cesupa.cardsystem.domain.entity;

import com.cesupa.cardsystem.domain.enums.BandeiraCartao;
import com.cesupa.cardsystem.domain.enums.StatusCartao;
import com.cesupa.cardsystem.domain.enums.TipoCartao;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;
import com.cesupa.cardsystem.domain.vo.Senha;

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
    private Senha senha;

    public Cartao(UUID id, CPF cpf, String nomeCompleto, DataDeNascimento dataNascimento, RendaMensal rendaMensal, TipoCartao tipo, BandeiraCartao bandeira, String numero, StatusCartao status) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.tipo = tipo;
        this.bandeira = bandeira;
        this.numero = numero;
        this.status = status;
    }

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

    public void atribuirId(UUID id) {
        this.id = id;
    }

    public Senha getSenha() { return senha; }

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

    public void ativar(CPF cpfInformado, Senha senhaInformada) {
        if (!this.cpf.equals(cpfInformado)) {
            throw new IllegalArgumentException("CPF não confere com o cartão.");
        }

        if (!(status == StatusCartao.APROVADO || status == StatusCartao.ENTREGUE)) {
            throw new IllegalStateException("Cartão não pode ser ativado. Status atual: " + status);
        }

        this.senha = senhaInformada;
        this.status = StatusCartao.ATIVO;
    }

    private static String gerarNumero() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
    }
}
