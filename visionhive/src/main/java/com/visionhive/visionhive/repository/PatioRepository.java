package com.visionhive.visionhive.repository;

import com.visionhive.visionhive.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PatioRepository extends JpaRepository<Patio, Long>, JpaSpecificationExecutor<Patio> {
}
