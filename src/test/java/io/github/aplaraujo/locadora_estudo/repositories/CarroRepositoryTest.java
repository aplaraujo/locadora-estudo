package io.github.aplaraujo.locadora_estudo.repositories;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class CarroRepositoryTest {
    @Autowired
    CarroRepository carroRepository;

    CarroEntity carro;

    @BeforeEach
    void setUp() {
        carro = new CarroEntity("Sedan", 100.00, 2024);
    }

    @Test
    void deveSalvarUmCarro() {
        carroRepository.save(carro);

        assertNotNull(carro.getId());
    }

    @Test
    void deveBuscarCarroPorId() {
        CarroEntity carro1 = carroRepository.save(carro);
        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carro1.getId());
        Assertions.assertThat(carroEncontrado).isPresent();
        Assertions.assertThat(carroEncontrado.get().getModelo()).isEqualTo("Sedan");
    }

    @Test
    void deveAtualizarUmCarro() {
        var carroSalvo = carroRepository.save(carro);
        carroSalvo.setAno(2025);
        var carroAtualizado = carroRepository.save(carroSalvo);

        Assertions.assertThat(carroAtualizado.getAno()).isEqualTo(2025);
    }

    @Test
    void deveExcluirCarro() {
        var carroSalvo = carroRepository.save(carro);
        carroRepository.deleteById(carroSalvo.getId());

        Optional<CarroEntity> carroEncontrado = carroRepository.findById(carroSalvo.getId());

        Assertions.assertThat(carroEncontrado).isEmpty();
    }
}