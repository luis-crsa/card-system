package com.cesupa.cardsystem.infrastructure.mapper;

import com.cesupa.cardsystem.application.usecase.dto.*;
import com.cesupa.cardsystem.dto.*;
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

    public static BloquearCartaoEntrada bloquearToInput(BloquearCartaoRequestDTO dto){
        return new BloquearCartaoEntrada(
                dto.numero(),
                dto.cpf(),
                dto.motivo()
        );
    }

    public static ComunicarPerdaRouboEntrada comunicarToInput(ComunicarPerdaRouboRequestDTO dto){
        return new ComunicarPerdaRouboEntrada(
                dto.numero(),
                dto.cpf(),
                dto.tipoDeOcorrencia()
        );
    }

    public static CancelarCartaoEntrada cancelarToInput(CancelarCartaoRequestDTO dto) {
        return new CancelarCartaoEntrada(
                dto.numero(),
                dto.cpf(),
                dto.motivo()
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

    public static CartaoMotivoResponseDTO toMotivoResponse(Cartao cartao) {
        return new CartaoMotivoResponseDTO(
                cartao.getId(),
                cartao.getNumero(),
                cartao.getStatus().name(),
                cartao.getMotivoBloqueio()
        );
    }

    public static CartaoTipoOcorrenciaResponseDTO toOcorrenciaResponse(Cartao cartao) {
        return new CartaoTipoOcorrenciaResponseDTO(
                cartao.getId(),
                cartao.getNumero(),
                cartao.getStatus().name(),
                cartao.getTipoDeOcorrencia().name()
        );
    }
}
