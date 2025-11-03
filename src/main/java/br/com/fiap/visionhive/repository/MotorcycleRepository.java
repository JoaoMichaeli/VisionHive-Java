package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {
    Optional<Object> findByPlaca(String placa);
}
