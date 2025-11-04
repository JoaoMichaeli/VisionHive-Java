package br.com.fiap.visionhive.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class BuzzerController {

    private static final String ESP32_IP = "http://192.168.1.5";

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/acionar-buzzer")
    public ResponseEntity<String> acionarBuzzer() {
        return chamarEsp32("/tocar");
    }

    @GetMapping("/parar-buzzer")
    public ResponseEntity<String> pararBuzzer() {
        return chamarEsp32("/parar");
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

    private ResponseEntity<String> chamarEsp32(String caminho) {
        try {
            restTemplate.getForObject(ESP32_IP + caminho, String.class);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao comunicar com ESP32: " + e.getMessage());
        }
    }
}
