package io.github.aplaraujo.locadora_estudo.repositories;

import io.github.aplaraujo.locadora_estudo.entities.CarroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<CarroEntity, Long> {
    List<CarroEntity> findByModelo(String modelo);
}
