package com.cesupa.cardsystem.domain.entity;

import com.cesupa.cardsystem.domain.enums.BandeiraCartao;
import com.cesupa.cardsystem.domain.enums.StatusCartao;
import com.cesupa.cardsystem.domain.enums.TipoCartao;
import com.cesupa.cardsystem.domain.enums.TipoDeOcorrencia;
import com.cesupa.cardsystem.domain.exception.CpfInvalidoException;
import com.cesupa.cardsystem.domain.exception.RendaInvalidaException;
import com.cesupa.cardsystem.domain.exception.SenhaInvalidaException;
import com.cesupa.cardsystem.domain.exception.StatusInvalidoException;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;
import com.cesupa.cardsystem.domain.vo.Senha;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    private String motivoBloqueio;
    private TipoDeOcorrencia tipoDeOcorrencia;

    public Cartao(UUID id, CPF cpf, String nomeCompleto, DataDeNascimento dataNascimento,
                  RendaMensal rendaMensal, TipoCartao tipo, BandeiraCartao bandeira,
                  String numero, StatusCartao status, Senha senha, String motivoBloqueio,
                  TipoDeOcorrencia tipoDeOcorrencia
                  ) {
        this.id = id;
        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.rendaMensal = rendaMensal;
        this.tipo = tipo;
        this.bandeira = bandeira;
        this.numero = numero;
        this.status = status;
        this.senha = senha;
        this.motivoBloqueio = motivoBloqueio;
        this.tipoDeOcorrencia = tipoDeOcorrencia;
    }

    public static Cartao solicitar(CPF cpf, String nomeCompleto, DataDeNascimento dataNascimento,
                                   RendaMensal renda, TipoCartao tipo, BandeiraCartao bandeira){

        if (!renda.atendeMinimoPara(tipo)) {
            throw new RendaInvalidaException("Renda insuficiente para tipo de cartão solicitado.");
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
                null,
                null,
                null
        );
    }
  
    public void ativar(CPF cpfInformado, Senha senhaInformada) {
        if (!this.cpf.equals(cpfInformado)) {
            throw new CpfInvalidoException("CPF não confere com o cartão.");
        }

        if (!(status == StatusCartao.APROVADO || status == StatusCartao.ENTREGUE)) {
            throw new StatusInvalidoException("Cartão não pode ser ativado. Status atual: " + status);
        }

        this.senha = senhaInformada;
        this.status = StatusCartao.ATIVO;
    }

    public void redefinirSenha(CPF cpfInformado, String senhaAntiga, String senhaNova, PasswordEncoder passwordEncoder) {
        if (!this.cpf.equals(cpfInformado)) {
            throw new CpfInvalidoException("CPF não corresponde ao titular do cartão.");
        }

        if (!passwordEncoder.matches(senhaAntiga, this.senha.valor())) {
            throw new SenhaInvalidaException("Senha antiga não confere.");
        }

        if (senhaAntiga.equals(senhaNova)) {
            throw new SenhaInvalidaException("A nova senha deve ser diferente da anterior.");
        }

        if (status != StatusCartao.ATIVO) {
            throw new StatusInvalidoException("Somente cartões ativos poder ter a senha redefinida. Status atual: " + status);
        }

        String novaHash = passwordEncoder.encode(senhaNova);
        this.senha = new Senha(novaHash);
    }


    public void bloquearTemporariamente(CPF cpfInformado, String motivo) {
        if (!this.cpf.equals(cpfInformado)) {
            throw new CpfInvalidoException("CPF não confere com o cartão.");
        }

        if (this.status != StatusCartao.ATIVO) {
            throw new StatusInvalidoException("Somente cartões ativos podem ser bloqueados temporariamente. Status atual: " + status);
        }

        this.status = StatusCartao.BLOQUEADO_TEMPORARIO;
        this.motivoBloqueio = motivo;
    }

    public void comunicacaoPerdaRoubo(CPF cpfInformado, TipoDeOcorrencia tipoDeOcorrencia ){
        if (!this.cpf.equals(cpfInformado)) {
            throw new CpfInvalidoException("CPF não confere com o cartão.");
        }

        if (this.status != StatusCartao.ATIVO) {
            throw new StatusInvalidoException("Somente cartões ativos podem ser bloqueados por perda ou roubo. Status atual: " + status);
        }

        this.status = StatusCartao.BLOQUEADO_PERDA_ROUBO;
        this.tipoDeOcorrencia = tipoDeOcorrencia;


    }

    public void cancelarDefinitivamente(String motivo) {
        if (this.status != StatusCartao.ATIVO && this.status != StatusCartao.BLOQUEADO_TEMPORARIO) {
            throw new RuntimeException("Apenas cartões ativos ou temporariamente bloqueados podem ser cancelados.");
        }

        this.status = StatusCartao.CANCELADO;
        this.motivoBloqueio = motivo;
    }


    private static String gerarNumero() {
        return String.valueOf((long) (Math.random() * 1_0000_0000_0000_0000L));
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

    public Senha getSenha() { return senha; }
  
    public String getMotivoBloqueio() {
        return motivoBloqueio;
    }

    public void atribuirId(UUID id) {
        this.id = id;
    }

    public TipoDeOcorrencia getTipoDeOcorrencia() {
        return tipoDeOcorrencia;
    }
}
