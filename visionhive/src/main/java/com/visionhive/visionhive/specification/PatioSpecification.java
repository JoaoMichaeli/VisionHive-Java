package com.visionhive.visionhive.specification;

import com.visionhive.visionhive.controller.PatioController;
import com.visionhive.visionhive.model.Patio;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;

public class PatioSpecification {

    public static Specification<Patio> withFilters(PatioController.PatioFilters filters){
        return (root, query, cb) -> {

            var predicates = new ArrayList<>();
            if(filters.nome() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"
                        )
                );
            }

            if(filters.branchId() != null){
                predicates.add(
                        cb.and(
                                cb.equal(root.get("branch").get("id"), filters.branchId())
                        )
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
