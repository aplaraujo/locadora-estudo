package io.github.aplaraujo.locadora_estudo.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test
    void deveCriarClienteComNome() {
        // Arrange
        var cliente = new Cliente("Maria");
        String nomeEsperado = "Maria";

        // Act
        String nome = cliente.getNome();

        // Assert
        assertNotNull(nome);
        assertEquals(nomeEsperado, nome);
        assertTrue(nome.startsWith("M"));
        assertFalse(nome.length() == 10);
        assertThat(nome).isEqualTo(nomeEsperado);
    }
}
