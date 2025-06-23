package com.cesupa.cardsystem.domain.exception;

public class IdadeInvalidaException extends RuntimeException {
    public IdadeInvalidaException() {
        super("Idade menor que 18 anos");
    }
}
