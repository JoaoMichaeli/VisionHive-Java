package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.controller.BranchController;
import br.com.fiap.visionhive.model.Branch;
import br.com.fiap.visionhive.repository.BranchRepository;
import br.com.fiap.visionhive.specification.BranchSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;

    public void save(Branch branch) {
        branchRepository.save(branch);
    }

    public Branch findById(Long id) {
        return branchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filial não encontrada"));
    }

    public List<Branch> findAllBranches() {
        return branchRepository.findAll();
    }

    public Page<Branch> findAllWithFilters(BranchController.BranchFilters filters, Pageable pageable) {
        var spec = BranchSpecification.withFilters(filters);
        return branchRepository.findAll(spec, pageable);
    }

    public long countAllBranches() {
        return branchRepository.count();
    }

    public long countWithFilters(BranchController.BranchFilters filters) {
        var spec = BranchSpecification.withFilters(filters);
        return branchRepository.count(spec);
    }

}
