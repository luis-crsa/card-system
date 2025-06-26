package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.*;
import com.cesupa.cardsystem.dto.*;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final AprovarCartaoUseCase aprovarCartaoUseCase;
    private final AtivarCartaoUseCase ativarCartaoUseCase;
    private final RedefinirSenhaUseCase redefinirSenhaUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;
    private final CancelarCartaoUseCase cancelarCartaoUseCase;
    private final ComunicarPerdaRouboUseCase comunicarPerdaRouboUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase,
                            AprovarCartaoUseCase aprovarCartaoUseCase,
                            AtivarCartaoUseCase ativarCartaoUseCase,
                            RedefinirSenhaUseCase redefinirSenhaUseCase,
                            BloquearCartaoUseCase bloquearCartaoUseCase,
                            CancelarCartaoUseCase cancelarCartaoUseCase,
                            ComunicarPerdaRouboUseCase comunicarPerdaRouboUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.aprovarCartaoUseCase = aprovarCartaoUseCase;
        this.ativarCartaoUseCase = ativarCartaoUseCase;
        this.redefinirSenhaUseCase = redefinirSenhaUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
        this.cancelarCartaoUseCase = cancelarCartaoUseCase;
        this.comunicarPerdaRouboUseCase = comunicarPerdaRouboUseCase;
    }

    @PostMapping("/solicitar")
    public ResponseEntity<CartaoResponseDTO> solicitar(@RequestBody SolitarCartaoRequestDTO dto) {
        var input = CartaoMapper.solicitarToInput(dto);
        var novoCartao = solicitarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(novoCartao);
        return ResponseEntity.status(201).body(resposta);
    }

    @PutMapping("/{numero}/aprovar")
    public ResponseEntity<String> ativar(@PathVariable String numero) {
        var resposta = aprovarCartaoUseCase.executar(numero);
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

    @PutMapping("/bloquear-temporariamente")
    public ResponseEntity<CartaoMotivoResponseDTO> bloquear(@RequestBody BloquearCartaoRequestDTO dto) {
        var input = CartaoMapper.bloquearToInput(dto);
        var cartao = bloquearCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toMotivoResponse(cartao);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/comunicar-perda-roubo")
    public ResponseEntity<CartaoTipoOcorrenciaResponseDTO> comunicarPerdaRoubo(@RequestBody ComunicarPerdaRouboRequestDTO dto) {
        var input = CartaoMapper.comunicarToInput(dto);
        var cartao = comunicarPerdaRouboUseCase.executar(input);
        var resposta = CartaoMapper.toOcorrenciaResponse(cartao);
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
