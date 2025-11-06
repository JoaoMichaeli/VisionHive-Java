package br.com.fiap.visionhive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "buzzer_log")
public class BuzzerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "acao", nullable = false)
    private String acao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "ip_esp32")
    private String ipEsp32;

    @Column(name = "placa_moto", length = 7)
    private String placaMoto;

    @Column(name = "sucesso", columnDefinition = "boolean")
    private Boolean sucesso;

    @Column(name = "erro")
    private String erro;
}
