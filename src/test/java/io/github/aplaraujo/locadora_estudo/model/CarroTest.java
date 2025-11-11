package io.github.aplaraujo.locadora_estudo.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CarroTest {
    @Test
    @DisplayName("Deve calcular o valor correto do aluguel")
    void deveCalcularValorAluguel() {
        // Arrange
        Carro carro = new Carro("Sedan", 100.00);
        Double valorEsperado = 300.00;

        // Act
        Double total = carro.calcularValorAluguel(3);

        // Assert
        Assertions.assertEquals(valorEsperado, total);
    }

    @Test
    @DisplayName("Deve calcular o valor correto do aluguel com desconto")
    void deveCalcularValorAluguelComDesconto() {
        // Arrange
        Carro carro = new Carro("Sedan", 100.00);
        int quantidadeDias = 5;
        Double valorEsperado = 450.00;

        // Act
        Double total = carro.calcularValorAluguel(quantidadeDias);

        // Assert
        Assertions.assertEquals(valorEsperado, total);
    }
}
