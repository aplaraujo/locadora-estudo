package io.github.aplaraujo.locadora_estudo.model;

public class Carro {
    private String modelo;
    private Double valorDiaria;

    public Carro() {}

    public Carro(String modelo, Double valorDiario) {
        this.modelo = modelo;
        this.valorDiaria = valorDiario;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getValorDiario() {
        return valorDiaria;
    }

    public void setValorDiario(Double valorDiario) {
        this.valorDiaria = valorDiario;
    }

    public Double calcularValorAluguel(Integer dias) {
        Double desconto = 0.00;
        if (dias >= 5) {
            desconto = 50.00;
        }
        return (dias * valorDiaria) - desconto;
    }
}
