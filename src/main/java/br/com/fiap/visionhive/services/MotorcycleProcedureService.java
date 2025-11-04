package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.Motorcycle;
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
    public ProcedureResponse atualizarSituacao(String placa, String novaSituacao) {
        Motorcycle moto = (Motorcycle) motorcycleRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));

        String jsonResponse = procedureRepo.atualizarSituacao(moto.getId(), novaSituacao);

        boolean isError = jsonResponse.contains("\"erro\"");
        String mensagem = isError ? jsonResponse : "Situação atualizada com sucesso";

        return new ProcedureResponse(jsonResponse, mensagem);
    }

    @Transactional
    public String associarPatio(String placa, Long patioId) {
        Motorcycle moto = (Motorcycle) motorcycleRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));

        patioRepository.findById(patioId)
                .orElseThrow(() -> new RuntimeException("Pátio não encontrado"));

        return procedureRepo.associarPatio(moto.getId(), patioId);
    }

    public int contarPatiosPorFilial(Long branchId) {
        return procedureRepo.contarPatiosPorFilial(branchId);
    }
}
