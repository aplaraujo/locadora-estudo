package io.github.aplaraujo.locadora_estudo.services;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import io.github.aplaraujo.locadora_estudo.exceptions.EntityNotFoundException;
import io.github.aplaraujo.locadora_estudo.repositories.CarroRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {
    @InjectMocks
    CarroService carroService;

    @Mock
    CarroRepository carroRepository;

    CarroEntity carro, carro1;

    @BeforeEach
    void setUp() {
        carro = new CarroEntity("Sedan", 100.00, 2024);
        carro1 = new CarroEntity("SUV", 0.00, 2024);
        carro.setId(1L);
    }

    @Test
    void deveSalvarUmCarro() {
        when(carroRepository.save(any())).thenReturn(carro);
        var carroSalvo = carroService.salvar(carro);
        assertNotNull(carroSalvo);
        assertEquals("Sedan", carroSalvo.getModelo());
        verify(carroRepository).save(any());
    }

    @Test
    void deveLancarErroAoSalvarCarroComDiariaNegativa() {
        var erro = catchThrowable(() -> carroService.salvar(carro1));
        assertThat(erro).isInstanceOf(IllegalArgumentException.class);
        verify(carroRepository, never()).save(any());
    }

    @Test
    void deveAtualizarUmCarroComSucesso() {
        var carroExistente = new CarroEntity("Gol", 80.00, 2026);
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carroExistente));

        var carroAtualizado = new CarroEntity("Gol", 80.00, 2026);
        carroAtualizado.setId(1L);
        when(carroRepository.save(any())).thenReturn(carroAtualizado);

        Long id = 1L;
        var resultado = carroService.atualizar(id, carro1);

        assertEquals("Gol", resultado.getModelo());
        verify(carroRepository, times(1)).save(any());
    }

    @Test
    void deveLancarErroAoTentarAtualizarCarroComIdInexistente() {
        Long id = 1L;
        when(carroRepository.findById(any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> carroService.atualizar(id, carro));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        verify(carroRepository, never()).save(any());
    }

    @Test
    void deveLancarErroAoTentarExcluirCarroComIdInexistente() {
        Long id = 1L;
        when(carroRepository.findById(any())).thenReturn(Optional.empty());

        var erro = catchThrowable(() -> carroService.excluir(id));
        assertThat(erro).isInstanceOf(EntityNotFoundException.class);
        verify(carroRepository, never()).delete(any());
    }

    @Test
    void deveExcluirUmCarroComSucesso() {
        Long id = 1L;
        var carro = new CarroEntity("Gol", 80.00, 2026);
        when(carroRepository.findById(any())).thenReturn(Optional.of(carro));

        carroService.excluir(id);

        verify(carroRepository, times(1)).delete(carro);
    }

    @Test
    void deveBuscarCarroPorId() {
        Long id = 1L;
        var carro = new CarroEntity("Gol", 80.00, 2026);
        when(carroRepository.findById(any())).thenReturn(Optional.of(carro));
        var carroEncontrado = carroService.buscarPorId(id);

        assertThat(carroEncontrado.getModelo()).isEqualTo("Gol");
        assertThat(carroEncontrado.getValorDiaria()).isEqualTo(80.00);
        assertThat(carroEncontrado.getAno()).isEqualTo(2026);
    }

    @Test
    void deveMostrarTodos() {
        var carro1 = new CarroEntity("Gol", 80.00, 2026);
        var carro2 = new CarroEntity("Hatch", 180.00, 2026);
        var lista = List.of(carro1, carro2);

        when(carroRepository.findAll()).thenReturn(lista);
        List<CarroEntity> resultado = carroService.listarTodos();

        assertThat(resultado).hasSize(2);
        verify(carroRepository, times(1)).findAll();
        verifyNoMoreInteractions(carroRepository);
    }
}