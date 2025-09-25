package br.com.fiap.visionhive.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Patio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do pátio não pode estar em branco")
    private String nome;

    @ManyToOne
    @JsonIgnore
    private Branch branch;

    @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Motorcycle> motorcycles = new ArrayList<>();

}
