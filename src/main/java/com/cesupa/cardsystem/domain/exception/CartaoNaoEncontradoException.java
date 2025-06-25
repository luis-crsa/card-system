package com.cesupa.cardsystem.domain.exception;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException() {
        super("Cartão não encontrado");
    }
}
