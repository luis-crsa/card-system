package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.AtivarCartaoEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.Senha;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AtivarCartaoUseCase {

    private final CartaoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AtivarCartaoUseCase(CartaoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cartao executar(AtivarCartaoEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());

        Senha senha = new Senha(entrada.senha());

        String hash = passwordEncoder.encode(senha.valor());
        Senha senhaCriptografada = new Senha(hash);

        Cartao cartao = repository.buscarPorNumero(entrada.numero())
                .orElseThrow(() -> new IllegalArgumentException("Cartão não encontrado."));

        cartao.ativar(cpf, senhaCriptografada);
        repository.salvar(cartao);
        return cartao;
    }
}