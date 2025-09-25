package br.com.fiap.visionhive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Motorcycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A placa da moto não pode estar em branco")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres")
    private String placa;

    @NotBlank(message = "O chassi da moto não pode estar em branco")
    @Size(min = 17, max = 17, message = "A numeração do chassi não pode estar em branco")
    private String chassi;

    @NotBlank(message = "A numeração do motor não pode estar em branco")
    @Size(min = 9, max = 17, message = "A numeração do motor não pode estar em branco")
    private String numeracaoMotor;

    @NotBlank(message = "O modelo da moto não pode estar vazio")
    @Pattern(
            regexp = "MottuSport|MottuE|MottuPop",
            message = "O modelo da moto deve ser MottuSport, MottuE ou MottuPop"
    )
    private String motorcycleModels;

    @NotBlank(message = "A situação da moto não pode estar vazio")
    private String situacao;

    @ManyToOne
    @JoinColumn(name = "patio_id")
    private Patio patio;
}
