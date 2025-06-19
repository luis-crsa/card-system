package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.*;
import com.cesupa.cardsystem.dto.*;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoMapper;
import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final AtivarCartaoUseCase ativarCartaoUseCase;
    private final RedefinirSenhaUseCase redefinirSenhaUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;
    private final CancelarCartaoUseCase cancelarCartaoUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase, AtivarCartaoUseCase ativarCartaoUseCase, RedefinirSenhaUseCase redefinirSenhaUseCase, BloquearCartaoUseCase bloquearCartaoUseCase, CancelarCartaoUseCase cancelarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.ativarCartaoUseCase = ativarCartaoUseCase;
        this.redefinirSenhaUseCase = redefinirSenhaUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
        this.cancelarCartaoUseCase = cancelarCartaoUseCase;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<CartaoResponseDTO> solicitar(@RequestBody SolitarCartaoRequestDTO dto) {
        var input = CartaoMapper.solicitarToInput(dto);
        var novoCartao = solicitarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(novoCartao);
        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/ativar")
    public ResponseEntity<CartaoSenhaResponseDTO> ativar(@RequestBody AtivarCartaoRequestDTO dto) {
        var input = CartaoMapper.ativarToInput(dto);
        var cartao = ativarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toSenhaResponse(cartao);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/redefinir-senha")
    public ResponseEntity<CartaoSenhaResponseDTO> redefiniSenha(@RequestBody RedefinirSenhaRequestDTO dto){
        var input = CartaoMapper.redefinirToInput(dto);
        var cartao = redefinirSenhaUseCase.executar(input);
        var resposta = CartaoMapper.toSenhaResponse(cartao);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/bloquear-temporariamente")
    public ResponseEntity<CartaoMotivoResponseDTO> bloquear(@RequestBody BloquearCartaoRequestDTO dto) {
        var input = CartaoMapper.bloquearToInput(dto);
        var cartao = bloquearCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toMotivoResponse(cartao);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/cancelar")
    public ResponseEntity<CartaoMotivoResponseDTO> cancelar(@RequestBody CancelarCartaoRequestDTO dto) {
        var input = CartaoMapper.cancelarToInput(dto);
        var cartao = cancelarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toMotivoResponse(cartao);
        return ResponseEntity.ok(resposta);
    }
}
