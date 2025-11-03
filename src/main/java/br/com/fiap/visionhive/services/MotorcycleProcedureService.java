package br.com.fiap.visionhive.services;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.model.ProcedureLog;
import br.com.fiap.visionhive.repository.MotorcycleProcedureRepository;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import br.com.fiap.visionhive.repository.PatioRepository;
import br.com.fiap.visionhive.repository.ProcedureLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MotorcycleProcedureService {

    private final MotorcycleProcedureRepository procedureRepo;
    private final MotorcycleRepository motorcycleRepository;
    private final PatioRepository patioRepository;
    private final ProcedureLogRepository logRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public ProcedureResponse atualizarSituacao(String placa, String novaSituacao) {
        Motorcycle moto = (Motorcycle) motorcycleRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));

        String before = toJson(moto);
        String jsonResponse = procedureRepo.atualizarSituacao(moto.getId(), novaSituacao);

        saveLog("atualizarSituacao", before, jsonResponse);

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

        String before = toJson(moto);

        String jsonResponse = procedureRepo.associarPatio(moto.getId(), patioId);

        saveLog("associarPatio", before, jsonResponse);

        return jsonResponse;
    }

    public int contarPatiosPorFilial(Long branchId) {
        return procedureRepo.contarPatiosPorFilial(branchId);
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "{\"error\": \"Falha ao serializar JSON\"}";
        }
    }

    private void saveLog(String procedureName, String before, String after) {
        String username = getCurrentUsername();
        ProcedureLog log = new ProcedureLog(procedureName, username, before, after);
        logRepository.save(log);
    }

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() ? auth.getName() : "anonymous";
    }

}
