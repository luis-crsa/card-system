package com.cesupa.cardsystem.domain.entity;

import com.cesupa.cardsystem.domain.enums.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lancamento {

    private final LocalDate data;
    private final String descricao;
    private final BigDecimal valor;
    private boolean pago;
    private final TipoTransacao tipo;

    public Lancamento(LocalDate data, String descricao, BigDecimal valor, boolean pago, TipoTransacao tipo) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
        this.tipo = tipo;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipo() { return tipo; }
}
