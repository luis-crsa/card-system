package com.cesupa.cardsystem.config;

import com.cesupa.cardsystem.application.usecase.*;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.infrastructure.persistence.CartaoJpaRepository;
import com.cesupa.cardsystem.infrastructure.persistence.ImplementacaoCartaoRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

    @Bean
    public SolicitarCartaoUseCase solicitarCartaoUseCase(CartaoRepository repository) {
        return new SolicitarCartaoUseCase(repository);
    }
  
    @Bean
    public AtivarCartaoUseCase ativarCartaoUseCase(CartaoRepository repository) {
        return new AtivarCartaoUseCase(repository);
    }

    @Bean
    public RedefinirSenhaUseCase redefinirSenhaUseCase(CartaoRepository repository) {
        return new RedefinirSenhaUseCase(repository);
    }

    @Bean
    public BloquearCartaoUseCase bloquearCartaoUseCase(CartaoRepository repository) {
        return new BloquearCartaoUseCase(repository);
    }

    @Bean
    public CartaoRepository cartaoRepository(CartaoJpaRepository jpa) {
        return new ImplementacaoCartaoRepositoryJpa(jpa);
    }

    @Bean
    public CancelarCartaoUseCase cancelarCartaoUseCase(CartaoRepository cartaoRepository) {
        return new CancelarCartaoUseCase(cartaoRepository);
    }
}
