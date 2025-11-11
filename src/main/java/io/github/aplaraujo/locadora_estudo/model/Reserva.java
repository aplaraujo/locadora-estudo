package io.github.aplaraujo.locadora_estudo.model;

import io.github.aplaraujo.locadora_estudo.exceptions.ReservaInvalidaException;

public class Reserva {
    private Carro carro;
    private Cliente cliente;
    private Integer quantidadeDias;

    public Reserva(Carro carro, Cliente cliente, Integer quantidadeDias) {
        if (quantidadeDias <= 0) {
            throw new ReservaInvalidaException("Reserva inválida!");
        }
        this.carro = carro;
        this.cliente = cliente;
        this.quantidadeDias = quantidadeDias;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(Integer quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public Double calcularTotal() {
        return this.carro.calcularValorAluguel(quantidadeDias);
    }
}
