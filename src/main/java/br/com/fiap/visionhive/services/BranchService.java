package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.model.Branch;
import br.com.fiap.visionhive.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
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

}
