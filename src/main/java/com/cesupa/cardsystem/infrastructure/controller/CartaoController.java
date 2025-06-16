package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.AtivarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.AtivarCartaoRequestDTO;
import com.cesupa.cardsystem.dto.SolitarCartaoRequestDTO;
import com.cesupa.cardsystem.dto.CartaoResponseDTO;
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
    private final BloquearCartaoUseCase bloquearCartaoUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase, AtivarCartaoUseCase ativarCartaoUseCase, BloquearCartaoUseCase bloquearCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.ativarCartaoUseCase = ativarCartaoUseCase;
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
    public  ResponseEntity<CartaoResponseDTO> ativar(@RequestBody AtivarCartaoRequestDTO dto) {
        var input = CartaoMapper.ativarToInput(dto);
        var cartao = ativarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(cartao);
        return ResponseEntity.ok(resposta);
    }
  
    @PutMapping("/bloquear")
    public ResponseEntity<String> bloquear(@RequestBody BloquearCartaoEntrada entrada) {
        bloquearCartaoUseCase.bloquear(entrada);
        return ResponseEntity.ok("Cartão bloqueado temporariamente com sucesso.");
    }
}
