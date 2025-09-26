package br.com.fiap.visionhive.dto;

import br.com.fiap.visionhive.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {
    @NotBlank(message = "O nome de usuário não pode estar em branco")
    private String username;

    @NotBlank(message = "A senha não pode estar em branco")
    @Size(min = 6, max = 12, message = "A senha deve ter entre 6 e 12 caracteres")
    private String password;

    private Role role;
}
