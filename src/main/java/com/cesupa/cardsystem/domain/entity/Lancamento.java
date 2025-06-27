package com.cesupa.cardsystem.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Lancamento {

    private LocalDate data;
    private String descricao;
    private BigDecimal valor;
    private boolean pago;

    public Lancamento(LocalDate data, String descricao, BigDecimal valor, boolean pago) {
        this.data = data;
        this.descricao = descricao;
        this.valor = valor;
        this.pago = pago;
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
}
