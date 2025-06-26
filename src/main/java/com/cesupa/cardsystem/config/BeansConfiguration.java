package com.cesupa.cardsystem.config;

import com.cesupa.cardsystem.application.usecase.*;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.infrastructure.persistence.CartaoJpaRepository;
import com.cesupa.cardsystem.infrastructure.persistence.ImplementacaoCartaoRepositoryJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BeansConfiguration {

    @Bean
    public CartaoRepository cartaoRepository(CartaoJpaRepository jpa) {
        return new ImplementacaoCartaoRepositoryJpa(jpa);
    }

    @Bean
    public SolicitarCartaoUseCase solicitarCartaoUseCase(CartaoRepository repository) {
        return new SolicitarCartaoUseCase(repository);
    }

    @Bean
    public AprovarCartaoUseCase aprovarCartaoUseCase(CartaoRepository repository) {
        return new AprovarCartaoUseCase(repository);
    }
  
    @Bean
    public AtivarCartaoUseCase ativarCartaoUseCase(CartaoRepository repository) {
        return new AtivarCartaoUseCase(repository, passwordEncoder());
    }

    @Bean
    public RedefinirSenhaUseCase redefinirSenhaUseCase(CartaoRepository repository) {
        return new RedefinirSenhaUseCase(repository, passwordEncoder());
    }

    @Bean
    public BloquearCartaoUseCase bloquearCartaoUseCase(CartaoRepository repository) {
        return new BloquearCartaoUseCase(repository);
    }

    @Bean
    public ComunicarPerdaRouboUseCase comunicarPerdaRouboUseCase(CartaoRepository repository){
        return new ComunicarPerdaRouboUseCase(repository);
    }

    @Bean
    public CancelarCartaoUseCase cancelarCartaoUseCase(CartaoRepository cartaoRepository) {
        return new CancelarCartaoUseCase(cartaoRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
