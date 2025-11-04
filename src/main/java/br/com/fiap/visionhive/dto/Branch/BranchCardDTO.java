package br.com.fiap.visionhive.dto.Branch;

public record BranchCardDTO(
        Long id,
        String nome,
        String bairro,
        String cnpj,
        long motorcycleCount
) {}
