package io.github.aplaraujo.locadora_estudo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "carro")
public class CarroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private Double valorDiaria;
    private Integer ano;

    public CarroEntity() {}

    public CarroEntity(String modelo, Double valorDiaria, Integer ano) {
        this.modelo = modelo;
        this.valorDiaria = valorDiaria;
        this.ano = ano;
    }

    public CarroEntity(Long id, String modelo, Double valorDiaria, Integer ano) {
        this(modelo, valorDiaria, ano);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(Double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }
}
