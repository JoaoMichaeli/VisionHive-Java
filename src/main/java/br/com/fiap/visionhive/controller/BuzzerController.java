package br.com.fiap.visionhive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class BuzzerController {

    private static final String ESP32_IP = "http://192.168.1.5";

    @GetMapping("/acionar-buzzer")
    public ResponseEntity<String> acionarBuzzer() {
        return chamarEsp32("/tocar");
    }

    @GetMapping("/parar-buzzer")
    public ResponseEntity<String> pararBuzzer() {
        return chamarEsp32("/parar");
    }

    private ResponseEntity<String> chamarEsp32(String caminho) {
        try {
            RestTemplate rest = new RestTemplate();
            rest.getForObject(ESP32_IP + caminho, String.class);
            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro: " + e.getMessage());
        }
    }
}
