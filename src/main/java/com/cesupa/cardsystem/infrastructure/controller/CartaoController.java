package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.dto.CartaoRequestDTO;
import com.cesupa.cardsystem.dto.CartaoResponseDTO;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
    }

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> solicitar(@RequestBody CartaoRequestDTO dto) {
        var input = CartaoMapper.toInput(dto);
        var novoCartao = solicitarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(novoCartao);
        return ResponseEntity.ok(resposta);
    }
}
