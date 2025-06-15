package com.cesupa.cardsystem.infrastructure.controller;

import com.cesupa.cardsystem.application.usecase.SolicitarCartaoUseCase;
import com.cesupa.cardsystem.dto.CartaoRequestDTO;
import com.cesupa.cardsystem.dto.CartaoResponseDTO;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoMapper;
import com.cesupa.cardsystem.application.usecase.BloquearCartaoUseCase;
import com.cesupa.cardsystem.application.usecase.dto.BloquearCartaoEntrada;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final SolicitarCartaoUseCase solicitarCartaoUseCase;
    private final BloquearCartaoUseCase bloquearCartaoUseCase;

    public CartaoController(SolicitarCartaoUseCase solicitarCartaoUseCase,
                            BloquearCartaoUseCase bloquearCartaoUseCase) {
        this.solicitarCartaoUseCase = solicitarCartaoUseCase;
        this.bloquearCartaoUseCase = bloquearCartaoUseCase;
    }

    @PostMapping
    public ResponseEntity<CartaoResponseDTO> solicitar(@RequestBody CartaoRequestDTO dto) {
        var input = CartaoMapper.toInput(dto);
        var novoCartao = solicitarCartaoUseCase.executar(input);
        var resposta = CartaoMapper.toResponse(novoCartao);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/bloquear")
    public ResponseEntity<String> bloquear(@RequestBody BloquearCartaoEntrada entrada) {
        bloquearCartaoUseCase.bloquear(entrada);
        return ResponseEntity.ok("Cartão bloqueado temporariamente com sucesso.");
    }
}
