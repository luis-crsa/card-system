package com.cesupa.cardsystem.infrastructure.mapper;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.DataDeNascimento;
import com.cesupa.cardsystem.domain.vo.RendaMensal;
import com.cesupa.cardsystem.domain.vo.Senha;
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
        if (domain.getSenha() != null) {
            entity.setSenha(domain.getSenha().valor());
        } else {
            entity.setSenha(null);
        }
        entity.setMotivoBloqueio(domain.getMotivoBloqueio());
        entity.setTipoDeOcorrencia(domain.getTipoDeOcorrencia());
        return entity;
    }

    public static Cartao toDomain(CartaoEntity entity) {
        Senha senha = entity.getSenha() != null ? new Senha(entity.getSenha()) : null;

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
                senha,
                entity.getMotivoBloqueio(),
                entity.getTipoDeOcorrencia()
        );
    }

}
