package br.com.fiap.visionhive.specification;

import br.com.fiap.visionhive.controller.PatioController;
import br.com.fiap.visionhive.model.Patio;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;

public class PatioSpecification {

    public static Specification<Patio> withFilters(PatioController.PatioFilters filters) {
        return (root, query, cb) -> {

            var predicates = new ArrayList<Predicate>();

            if (filters.nome() != null && !filters.nome().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%")
                );
            }

            if (filters.branchNome() != null && !filters.branchNome().isBlank()) {
                predicates.add(
                        cb.like(cb.lower(root.get("branch").get("nome")), "%" + filters.branchNome().toLowerCase() + "%")
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
