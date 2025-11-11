package io.github.aplaraujo.locadora_estudo.services;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import io.github.aplaraujo.locadora_estudo.exceptions.EntityNotFoundException;
import io.github.aplaraujo.locadora_estudo.repositories.CarroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarroService {
    private final CarroRepository carroRepository;

    public CarroService(CarroRepository carroRepository) {
        this.carroRepository = carroRepository;
    }

    public CarroEntity salvar(CarroEntity carro) {
        if (carro.getValorDiaria() <= 0.00) {
            throw new IllegalArgumentException("O valor da diária não deve ser negativo ou igual a zero!");
        }
        return carroRepository.save(carro);
    }

    public CarroEntity atualizar(Long id, CarroEntity carroAtualizado) {
        var carroExistente = carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));
        carroExistente.setAno(carroAtualizado.getAno());
        carroExistente.setModelo(carroAtualizado.getModelo());
        carroExistente.setValorDiaria(carroAtualizado.getValorDiaria());

        return carroRepository.save(carroExistente);
    }

    public void excluir(Long id) {
        var carroExistente = carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));
        carroRepository.delete(carroExistente);
    }

    public CarroEntity buscarPorId(Long id) {
        return carroRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro não encontrado!"));
    }

    public List<CarroEntity> listarTodos() {
        return carroRepository.findAll();
    }
}
