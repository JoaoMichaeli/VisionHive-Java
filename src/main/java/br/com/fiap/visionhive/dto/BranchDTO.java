package br.com.fiap.visionhive.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDTO {
    @NotBlank(message = "O nome da filial não pode estar em branco")
    private String nome;

    @NotBlank(message = "O bairro da filial não pode estar em branco")
    private String bairro;

    @NotBlank(message = "O CNPJ da filial não pode estar em branco")
    private String cnpj;

    private MultipartFile image;
}
