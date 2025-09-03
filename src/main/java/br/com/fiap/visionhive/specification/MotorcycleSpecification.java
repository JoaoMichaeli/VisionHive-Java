package br.com.fiap.visionhive.specification;

import br.com.fiap.visionhive.controller.MotorcycleController;
import br.com.fiap.visionhive.model.Motorcycle;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;

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

            if(filters.situacao() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("situacao")), "%" + filters.situacao().toLowerCase() + "%"
                        )
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
