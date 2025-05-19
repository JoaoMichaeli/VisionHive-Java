package com.visionhive.visionhive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

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

    @NotEmpty(message = "O modelo da moto não pode estar vazio")
    @ElementCollection
    private List<MotorcycleGroup> motorcycleModels;

    @ManyToOne
    @JoinColumn(name = "patio_id")
    private Patio patio;

}
