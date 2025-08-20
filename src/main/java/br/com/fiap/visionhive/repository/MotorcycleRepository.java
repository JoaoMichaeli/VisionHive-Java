package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {
}
