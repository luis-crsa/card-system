package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.AtivarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.RedefinirSenhaUseCase;
import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.dto.*;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoMapper;
import com.cesupa.cardsystem.application.usecase.BloquearCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final AtivarCartaoUseCase ativarCartaoUseCase;
    private final RedefinirSenhaUseCase redefinirSenhaUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase, AtivarCartaoUseCase ativarCartaoUseCase, RedefinirSenhaUseCase redefinirSenhaUseCase, BloquearCartaoUseCase bloquearCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.ativarCartaoUseCase = ativarCartaoUseCase;
        this.redefinirSenhaUseCase = redefinirSenhaUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<CartaoResponseDTO> solicitar(@RequestBody SolitarCartaoRequestDTO dto) {
        var input = CartaoMapper.solicitarToInput(dto);
        var novoCartao = solicitarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(novoCartao);
        return ResponseEntity.ok(resposta);
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

    @PutMapping("/bloquear")
    public ResponseEntity<CartaoMotivoResponseDTO> bloquear(@RequestBody BloquearCartaoRequestDTO dto) {
        var input = CartaoMapper.bloquearToInput(dto);
        var cartao = bloquearCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toMotivoResponse(cartao);
        return ResponseEntity.ok(resposta);
    }
}
