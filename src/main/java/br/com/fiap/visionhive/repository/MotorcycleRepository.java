package br.com.fiap.visionhive.repository;

import br.com.fiap.visionhive.model.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long>, JpaSpecificationExecutor<Motorcycle> {
    Optional<Object> findByPlaca(String placa);

    @Query("""
        SELECT p.branch.id, COUNT(m.id)
        FROM Motorcycle m
        JOIN m.patio p
        WHERE p.branch IS NOT NULL
        GROUP BY p.branch.id
        """)
    List<Object[]> countMotorcyclesByBranch();

    @Query("""
        SELECT m.patio.id, COUNT(m.id)
        FROM Motorcycle m
        WHERE m.patio IS NOT NULL
        GROUP BY m.patio.id
        """)
    List<Object[]> countMotorcyclesByPatio();
}
