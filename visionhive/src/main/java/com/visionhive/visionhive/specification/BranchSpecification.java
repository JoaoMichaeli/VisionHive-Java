package com.visionhive.visionhive.specification;

import com.visionhive.visionhive.controller.BranchController;
import com.visionhive.visionhive.model.Branch;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;

public class BranchSpecification {

    public static Specification<Branch> withFilters(BranchController.BranchFilters filters){
        return (root,query,cb) -> {

            var predicates = new ArrayList<Predicate>();

            if(filters.nome() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("nome")), "%" + filters.nome().toLowerCase() + "%"
                        )
                );
            }

            if(filters.bairro() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("bairro")), "%" + filters.bairro().toLowerCase() + "%"
                        )
                );
            }

            if(filters.cnpj() != null){
                predicates.add(
                        cb.like(
                                cb.lower(root.get("cnpj")), "%" + filters.cnpj().toLowerCase() + "%"
                        )
                );
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
