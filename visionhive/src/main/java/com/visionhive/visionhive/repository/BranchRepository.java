package com.visionhive.visionhive.repository;

import com.visionhive.visionhive.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    @Query("SELECT b FROM Branch b LEFT JOIN FETCH b.patios")
    List<Branch> findAllWithPatios();
}
