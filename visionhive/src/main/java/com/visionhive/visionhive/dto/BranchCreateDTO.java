package com.visionhive.visionhive.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchCreateDTO {
    @NotBlank(message = "O nome da filial não pode estar em branco")
    private String nome;

    @NotBlank(message = "O bairro da filial não pode estar em branco")
    private String bairro;

    @NotBlank(message = "O CNPJ da filial não pode estar em branco")
    private String cnpj;
}

