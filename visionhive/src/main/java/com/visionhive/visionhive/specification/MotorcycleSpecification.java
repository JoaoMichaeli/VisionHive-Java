package com.visionhive.visionhive.specification;

import com.visionhive.visionhive.controller.MotorcycleController;
import com.visionhive.visionhive.model.Motorcycle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;

public class MotorcycleSpecification {

    public static Specification<Motorcycle> withFilters(MotorcycleController.MotorcycleFilters filters){
        return (root, query, cb) -> {

            var predicates = new ArrayList<>();
            if(filters.placa() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("placa")), "%" + filters.placa().toLowerCase() + "%"
                        )
                );
            }

            if(filters.chassi() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("chassi")), "%" + filters.chassi().toLowerCase() + "%"
                        )
                );
            }

            if(filters.numeracaoMotor() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("numeracaoMotor")), "%" + filters.numeracaoMotor().toLowerCase() + "%"
                        )
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
