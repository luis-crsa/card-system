package com.cesupa.cardsystem.application.usecase;

import com.cesupa.cardsystem.application.usecase.dto.RedefinirSenhaEntrada;
import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.exception.CartaoNaoEncontradoException;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.domain.vo.CPF;
import com.cesupa.cardsystem.domain.vo.Senha;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RedefinirSenhaUseCase {

    private final CartaoRepository repository;
    private final PasswordEncoder passwordEncoder;

    public RedefinirSenhaUseCase(CartaoRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Cartao executar(RedefinirSenhaEntrada entrada) {
        CPF cpf = new CPF(entrada.cpf());
        Senha novaSenhaValidada = new Senha(entrada.senhaNova());
        
        Cartao cartao = repository.buscarPorNumero(entrada.numero())
                .orElseThrow(CartaoNaoEncontradoException::new);
        cartao.redefinirSenha(cpf, entrada.senhaAntiga(), novaSenhaValidada.valor(), passwordEncoder);
        repository.salvar(cartao);
        return cartao;
    }
}