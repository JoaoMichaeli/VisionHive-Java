package br.com.fiap.visionhive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotorcycleDTO {
    @NotBlank(message = "A placa da moto não pode estar em branco")
    @Size(min = 7, max = 7, message = "A placa deve ter exatamente 7 caracteres")
    String placa;

    @NotBlank(message = "O chassi da moto não pode estar em branco")
    @Size(min = 17, max = 17, message = "A numeração do chassi não pode estar em branco")
    String chassi;

    @NotBlank(message = "A numeração do motor não pode estar em branco")
    @Size(min = 9, max = 17, message = "A numeração do motor não pode estar em branco")
    String numeracaoMotor;

    @NotEmpty(message = "O modelo da moto não pode estar vazio")
    String motorcycleModels;

    @NotNull(message = "O ID do pátio não pode ser nulo")
    private Long patioId;
}