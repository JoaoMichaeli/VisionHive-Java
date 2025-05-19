package com.visionhive.visionhive.repository;

import com.visionhive.visionhive.model.Motorcycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {

}
