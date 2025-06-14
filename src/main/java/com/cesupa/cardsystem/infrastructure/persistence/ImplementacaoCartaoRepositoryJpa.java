package com.cesupa.cardsystem.infrastructure.persistence;

import com.cesupa.cardsystem.domain.entity.Cartao;
import com.cesupa.cardsystem.domain.repository.CartaoRepository;
import com.cesupa.cardsystem.infrastructure.mapper.CartaoEntityMapper;

import java.util.Optional;
import java.util.UUID;

public class ImplementacaoCartaoRepositoryJpa implements CartaoRepository {

    private final CartaoJpaRepository jpa;

    public ImplementacaoCartaoRepositoryJpa(CartaoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Cartao cartao) {
        CartaoEntity entity = CartaoEntityMapper.toEntity(cartao);

        CartaoEntity salvo = jpa.save(entity);

        cartao.atribuirId(salvo.getId());
    }

    @Override
    public Optional<Cartao> buscarPorId(UUID id) {
        return jpa.findById(id).map(CartaoEntityMapper::toDomain);
    }
}
