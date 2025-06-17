package com.cesupa.cardsystem.application.usecase.dto;

public record RedefinirSenhaEntrada(
        String numero,
        String cpf,
        String senhaAntiga,
        String senhaNova
) {}
