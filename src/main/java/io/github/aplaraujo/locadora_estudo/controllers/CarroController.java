package io.github.aplaraujo.locadora_estudo.controllers;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import io.github.aplaraujo.locadora_estudo.exceptions.EntityNotFoundException;
import io.github.aplaraujo.locadora_estudo.services.CarroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/carros")
public class CarroController {
    private final CarroService carroService;

    public CarroController(CarroService carroService) {
        this.carroService = carroService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody CarroEntity carro) {
        try {
            var carroSalvo = carroService.salvar(carro);
            return ResponseEntity.status(HttpStatus.CREATED).body(carroSalvo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CarroEntity> detalhesCarro(@PathVariable Long id) {
        try {
           var carroEncontrado = carroService.buscarPorId(id);
           return ResponseEntity.ok(carroEncontrado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CarroEntity>> mostrar() {
        return ResponseEntity.ok(carroService.listarTodos());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody CarroEntity carroAtualizado) {
        try {
            carroService.atualizar(id, carroAtualizado);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            carroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
