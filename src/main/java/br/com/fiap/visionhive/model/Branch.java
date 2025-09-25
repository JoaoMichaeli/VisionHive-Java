package br.com.fiap.visionhive.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da filial não pode estar em branco")
    private String nome;

    @NotBlank(message = "O bairro não pode estar em branco")
    private String bairro;

    @NotBlank(message = "O cnpj não pode estar em branco")
    @Size(min = 14, max = 14, message= "O cnpj deve ter exatamente 14 digitos")
    private String cnpj;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Patio> patios = new ArrayList<>();

}
