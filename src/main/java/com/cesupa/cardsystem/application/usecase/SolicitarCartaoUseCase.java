package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.SolicitarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.enums.BandeiraCartao;
import com.cesupa.cardsystem.domain.enums.TipoCartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;

public class SolicitarCartaoUseCase {

    private final CartaoRepository repository;

    public SolicitarCartaoUseCase(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao executar(SolicitarCartaoEntrada input) {
        CPF cpf = new CPF(input.cpf());
        DataDeNascimento nascimento = new DataDeNascimento(input.dataNascimento());
        RendaMensal renda = new RendaMensal(input.rendaMensal());
        var tipo = TipoCartao.valueOf(input.tipoCartao().toUpperCase());
        var bandeira = BandeiraCartao.valueOf(input.bandeiraCartao().toUpperCase());

        Cartao novo = Cartao.solicitar(cpf, input.nomeCompleto(), nascimento, renda, tipo, bandeira);

        repository.salvar(novo);
        return novo;
    }
}
