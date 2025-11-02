package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.repository.MotorcycleProcedureRepository;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import br.com.fiap.visionhive.repository.PatioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotorcycleProcedureService {

    private final MotorcycleProcedureRepository procedureRepo;
    private final MotorcycleRepository motorcycleRepository;
    private final PatioRepository patioRepository;

    @Transactional
    public ProcedureResponse atualizarSituacao(Long motoId, String novaSituacao) {
        motorcycleRepository.findById(motoId)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));

        return procedureRepo.atualizarSituacao(motoId, novaSituacao);
    }

    @Transactional
    public String associarPatio(Long motoId, Long patioId) {
        motorcycleRepository.findById(motoId)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        return procedureRepo.associarPatio(motoId, patioId);
    }

    public int contarPatiosPorFilial(Long branchId) {
        System.out.println("Contando pátios para filial ID: " + branchId);
        int result = procedureRepo.contarPatiosPorFilial(branchId);
        System.out.println("Total de pátios: " + result);
        return result;
    }
}
