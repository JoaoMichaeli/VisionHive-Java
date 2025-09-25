package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatioRepository extends JpaRepository<Patio, Long>, JpaSpecificationExecutor<Patio> {}
