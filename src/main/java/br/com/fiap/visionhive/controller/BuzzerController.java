package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.BuzzerLog;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.repository.BuzzerLogRepository;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BuzzerController {

    private String estadoBuzzerGlobal = "PARAR";

    private Map<String, Object> ultimoStatusEsp32 = new ConcurrentHashMap<>();
    private LocalDateTime ultimaAtualizacaoEsp32 = null;

    private final BuzzerLogRepository buzzerLogRepository;
    private final MotorcycleRepository motorcycleRepository;


    @GetMapping("/acionar-buzzer/moto/{id}")
    public ResponseEntity<String> acionarBuzzerPorId(@PathVariable Long id) {
        return registrarComandoGlobal("ACIONAR", id);
    }

    @GetMapping("/parar-buzzer/moto/{id}")
    public ResponseEntity<String> pararBuzzerPorId(@PathVariable Long id) {
        return registrarComandoGlobal("PARAR", id);
    }


    @GetMapping("/comando-global-esp")
    public ResponseEntity<Map<String, String>> getComandoGlobalParaEsp() {
        Map<String, String> response = new HashMap<>();
        response.put("comando", estadoBuzzerGlobal);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/esp-status-report")
    public ResponseEntity<String> receiveEspStatus(@RequestBody Map<String, Object> statusData) {
        ultimoStatusEsp32 = statusData;
        ultimaAtualizacaoEsp32 = LocalDateTime.now();
        System.out.println("Status do ESP32 recebido: " + statusData);
        return ResponseEntity.ok("Status do ESP32 recebido com sucesso!");
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getEspStatus() {
        if (ultimoStatusEsp32.isEmpty() || ultimaAtualizacaoEsp32 == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("connected", false);
            error.put("error", "Nenhum status do ESP32 recebido ainda.");
            return ResponseEntity.status(200).body(error);
        }

        boolean connected = ultimaAtualizacaoEsp32.isAfter(LocalDateTime.now().minusSeconds(15));
        ultimoStatusEsp32.put("connected", connected);

        ultimoStatusEsp32.put("lastUpdateTimestamp", ultimaAtualizacaoEsp32.toString());

        return ResponseEntity.ok(ultimoStatusEsp32);
    }

    private ResponseEntity<String> registrarComandoGlobal(String acao, Long motoId) {
        Motorcycle moto = motorcycleRepository.findById(motoId)
                .orElse(null);

        String placa = (moto != null) ? moto.getPlaca() : "ID_DESCONHECIDO:" + motoId;

        BuzzerLog log = new BuzzerLog();
        log.setAcao(acao);
        log.setIpEsp32("Azure-Cloud-Global-Command");
        log.setDataHora(LocalDateTime.now());
        log.setPlacaMoto(placa);
        log.setSucesso(true);

        estadoBuzzerGlobal = acao;
        LocalDateTime.now();

        buzzerLogRepository.save(log);

        return ResponseEntity.ok("Comando '" + acao + "' registrado globalmente pelo acionamento da moto: " + placa);
    }
}
