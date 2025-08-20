package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
