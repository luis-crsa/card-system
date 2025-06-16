package com.cesupa.cardsystem.infrastructure.mapper;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;
import com.cesupa.cardsystem.infrastructure.persistence.CartaoEntity;

public class CartaoEntityMapper {

    public static CartaoEntity toEntity(Cartao domain) {
        CartaoEntity entity = new CartaoEntity();
        entity.setId(domain.getId());
        entity.setCpf(domain.getCpf().valor());
        entity.setNomeCompleto(domain.getNomeCompleto());
        entity.setDataNascimento(domain.getDataNascimento().data());
        entity.setRendaMensal(domain.getRendaMensal().valor());
        entity.setTipo(domain.getTipo());
        entity.setBandeira(domain.getBandeira());
        entity.setNumero(domain.getNumero());
        entity.setStatus(domain.getStatus());
        entity.setMotivoBloqueio(domain.getMotivoBloqueio());
        return entity;
    }

    public static Cartao toDomain(CartaoEntity entity) {
        return new Cartao(
                entity.getId(),
                new CPF(entity.getCpf()),
                entity.getNomeCompleto(),
                new DataDeNascimento(entity.getDataNascimento()),
                new RendaMensal(entity.getRendaMensal()),
                entity.getTipo(),
                entity.getBandeira(),
                entity.getNumero(),
                entity.getStatus(),
                entity.getMotivoBloqueio()
        );
    }
}
