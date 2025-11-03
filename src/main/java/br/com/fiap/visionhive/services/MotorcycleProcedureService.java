package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.model.ProcedureLog;
import br.com.fiap.visionhive.repository.MotorcycleProcedureRepository;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import br.com.fiap.visionhive.repository.PatioRepository;
import br.com.fiap.visionhive.repository.ProcedureLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MotorcycleProcedureService {

    private final MotorcycleProcedureRepository procedureRepo;
    private final MotorcycleRepository motorcycleRepository;
    private final PatioRepository patioRepository;
    private final ProcedureLogRepository logRepository;

    @Transactional
    public ProcedureResponse atualizarSituacao(String placa, String novaSituacao) {
        Motorcycle moto = (Motorcycle) motorcycleRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));

        ProcedureResponse resp = procedureRepo.atualizarSituacao(moto.getId(), novaSituacao);

        saveLogIfJson(resp.json());

        return resp;
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
        System.out.println("Contando pátios para filial ID: " + branchId);
        int result = procedureRepo.contarPatiosPorFilial(branchId);
        System.out.println("Total de pátios: " + result);
        return result;
    }

    private void saveLogIfJson(String json) {
        if (json != null && !json.trim().isEmpty()) {
            ProcedureLog log = new ProcedureLog(
                    null,
                    "atualizarSituacao",
                    getCurrentUsername(),
                    json,
                    LocalDateTime.now()
            );
            logRepository.save(log);
        }
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() ? auth.getName() : "anonymous";
    }
}

