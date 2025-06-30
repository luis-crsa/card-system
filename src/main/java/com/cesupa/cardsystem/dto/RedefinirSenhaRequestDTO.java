package com.cesupa.cardsystem.dto;

public record RedefinirSenhaRequestDTO(
        String numero,
        String cpf,
        String senhaAntiga,
        String senhaNova
) {}
