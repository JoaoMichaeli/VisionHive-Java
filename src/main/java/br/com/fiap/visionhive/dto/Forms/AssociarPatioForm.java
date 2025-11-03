package br.com.fiap.visionhive.dto.Forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AssociarPatioForm(@NotBlank(message = "Placa é obrigatória")
                                @Size(min = 7, max = 7, message = "Placa deve ter 7 caracteres")
                                @Pattern(regexp = "^[A-Z]{3}[0-9][0-9A-Z][0-9]{2}$",
                                        message = "Placa inválida. Ex: BRA2E19")
                                String placa,

                                @NotNull(message = "ID do pátio é obrigatório")
                                Long patioId) {}
