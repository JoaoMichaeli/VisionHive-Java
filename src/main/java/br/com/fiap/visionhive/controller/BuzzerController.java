package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.BuzzerLog;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.repository.BuzzerLogRepository;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuzzerController {

    private static final String ESP32_IP = "http://192.168.1.5";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BuzzerLogRepository buzzerLogRepository;
    private final MotorcycleRepository motorcycleRepository;

    @GetMapping("/acionar-buzzer/moto/{id}")
    public ResponseEntity<String> acionarBuzzerPorId(@PathVariable Long id) {
        return executarComLogPorId("/tocar", "ACIONAR", id);
    }

    @GetMapping("/parar-buzzer/moto/{id}")
    public ResponseEntity<String> pararBuzzerPorId(@PathVariable Long id) {
        return executarComLogPorId("/parar", "PARAR", id);
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getEspStatus() {
        try {
            String json = restTemplate.getForObject(ESP32_IP + "/status", String.class);
            Map<String, Object> data = objectMapper.readValue(json, new TypeReference<>() {});
            data.put("connected", true);
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("connected", false);
            error.put("error", "ESP32 não responde");
            error.put("details", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    private ResponseEntity<String> executarComLogPorId(String caminho, String acao, Long motoId) {
        Motorcycle moto = motorcycleRepository.findById(motoId)
                .orElse(null);

        if (moto == null) {
            return ResponseEntity.status(404).body("Moto não encontrada: ID " + motoId);
        }

        String placa = moto.getPlaca();

        BuzzerLog log = new BuzzerLog();
        log.setAcao(acao);
        log.setIpEsp32(ESP32_IP);
        log.setDataHora(LocalDateTime.now());
        log.setPlacaMoto(placa);

        try {
            restTemplate.getForObject(ESP32_IP + caminho, String.class);
            log.setSucesso(true);
            buzzerLogRepository.save(log);
            return ResponseEntity.ok("OK - Placa: " + placa);
        } catch (Exception e) {
            log.setSucesso(false);
            log.setErro(e.getMessage());
            buzzerLogRepository.save(log);
            return ResponseEntity.status(500).body("Erro ao comunicar com ESP32: " + e.getMessage());
        }
    }
}
