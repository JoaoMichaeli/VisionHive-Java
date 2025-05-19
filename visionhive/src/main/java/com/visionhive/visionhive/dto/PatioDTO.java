package com.visionhive.visionhive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatioDTO {
    @NotBlank(message = "O nome do pátio não pode estar em branco")
    private String nome;

    @NotNull(message = "O ID da filial não pode ser nulo")
    private Long branchId;
}
