package com.cesupa.cardsystem.infrastructure.mapper;

import com.cesupa.cardsystem.application.usecase.dto.AtivarCartaoEntrada;
import com.cesupa.cardsystem.application.usecase.dto.RedefinirSenhaEntrada;
import com.cesupa.cardsystem.dto.*;
import com.cesupa.cardsystem.application.usecase.dto.SolicitarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;

public class CartaoMapper {

    public static SolicitarCartaoEntrada solicitarToInput(SolitarCartaoRequestDTO dto) {
        return new SolicitarCartaoEntrada(
                dto.cpf(),
                dto.nomeCompleto(),
                dto.dataNascimento(),
                dto.rendaMensal(),
                dto.tipoCartao(),
                dto.bandeiraCartao()
        );
    }

    public static CartaoResponseDTO toResponse(Cartao cartao) {
        return new CartaoResponseDTO(
                cartao.getId(),
                cartao.getNumero(),
                cartao.getStatus().name(),
                cartao.getTipo().name(),
                cartao.getBandeira().name()
        );
    }

    public static CartaoSenhaResponseDTO toSenhaResponse(Cartao cartao) {
        return new CartaoSenhaResponseDTO(
                cartao.getId(),
                cartao.getNumero(),
                cartao.getStatus().name(),
                cartao.getTipo().name(),
                cartao.getBandeira().name(),
                cartao.getSenha().valor()
        );
    }

    public static AtivarCartaoEntrada ativarToInput(AtivarCartaoRequestDTO dto){
        return new AtivarCartaoEntrada(
                dto.numero(),
                dto.cpf(),
                dto.senha()
        );
    }

    public static RedefinirSenhaEntrada redefinirToInput(RedefinirSenhaRequestDTO dto){
        return new RedefinirSenhaEntrada(
                dto.numero(),
                dto.cpf(),
                dto.senhaAntiga(),
                dto.senhaNova()
        );
    }
}

