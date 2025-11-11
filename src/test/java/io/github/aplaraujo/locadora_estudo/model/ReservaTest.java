package io.github.aplaraujo.locadora_estudo.model;

import static org.assertj.core.api.Assertions.*;

import io.github.aplaraujo.locadora_estudo.exceptions.ReservaInvalidaException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    Cliente cliente;
    Carro carro;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("José");
        carro = new Carro("Sedan", 100.00);
    }

    @Test
    void deveCriarUmaReserva() {

        var dias = 5;
        Reserva reserva = new Reserva(carro, cliente, dias);

        assertThat(reserva).isNotNull();
    }

    @Test
    void deveDarErroAoCriarUmaReservaComDiasNegativos() {
        assertThrows(ReservaInvalidaException.class, () -> {
            new Reserva(carro, cliente, 0);
        });
        assertDoesNotThrow(() ->  new Reserva(carro, cliente, 1));

        // AssertJ
        var erro = catchThrowable(() -> new Reserva(carro, cliente, 0));
        assertThat(erro).isInstanceOf(ReservaInvalidaException.class).hasMessage("Reserva inválida!");
    }

    @Test
    void deveCalcularOTotalDoAluguel() {
        var dias = 3;
        var valorEsperado = 300.00;
        Reserva reserva = new Reserva(carro, cliente, dias);
        var aluguel = reserva.calcularTotal();
        assertThat(aluguel).isEqualTo(valorEsperado);
    }
}