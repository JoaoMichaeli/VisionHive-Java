package com.visionhive.visionhive.controller;

import com.visionhive.visionhive.dto.MotorcycleDTO;
import com.visionhive.visionhive.model.Motorcycle;
import com.visionhive.visionhive.repository.MotorcycleRepository;
import com.visionhive.visionhive.repository.PatioRepository;
import com.visionhive.visionhive.specification.MotorcycleSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("motorcycles")
@Slf4j
public class MotorcycleController {

    public record MotorcycleFilters (String placa, String chassi, String numeracaoMotor){}

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private PatioRepository patioRepository;

    @GetMapping
    @Operation(summary = "Listar todas as motocicletas com filtros e paginação", description = "Retorna uma lista paginada de motocicletas com filtros opcionais")
    @Cacheable("motorcycles")
    public Page<Motorcycle> index(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String numeracaoMotor,
            @ParameterObject @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var filters = new MotorcycleFilters(placa, chassi, numeracaoMotor);
        var specification = MotorcycleSpecification.withFilters(filters);
        return motorcycleRepository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "motorcycles", allEntries = true)
    @Operation(summary = "Inserir motocicletas", description = "Inserir uma motocicleta nova", responses = @ApiResponse(responseCode = "400", description = "Falha na validação"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public Motorcycle create(@RequestBody @Valid MotorcycleDTO dto){
        log.info("Cadastrando motocicleta: " + dto.getPlaca());

        var patio = patioRepository.findById(dto.getPatioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        var motorcycle = Motorcycle.builder()
                .placa(dto.getPlaca())
                .chassi(dto.getChassi())
                .numeracaoMotor(dto.getNumeracaoMotor())
                .motorcycleModels(dto.getMotorcycleModels())
                .patio(patio)
                .build();

        return motorcycleRepository.save(motorcycle);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar motocicleta", description = "Retorna a moto buscada pelo ID")
    public ResponseEntity<Motorcycle> get(@PathVariable Long id){
        log.info("Buscando motocicleta: " + id);
        return ResponseEntity.ok(getMotorcycle(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar motocicleta", description = "Deleta a moto escolhida")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deletando motocicleta: " + id);
        motorcycleRepository.delete(getMotorcycle(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar motocicleta", description = "Atualizar os dados da motocicleta")
    public ResponseEntity<Motorcycle> update(@PathVariable Long id, @RequestBody @Valid MotorcycleDTO dto){
        log.info("Atualizando motocicleta: " + id + " com " + dto);

        var oldMotorcycle = getMotorcycle(id);
        var patio = patioRepository.findById(dto.getPatioId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));

        oldMotorcycle.setPlaca(dto.getPlaca());
        oldMotorcycle.setChassi(dto.getChassi());
        oldMotorcycle.setNumeracaoMotor(dto.getNumeracaoMotor());
        oldMotorcycle.setMotorcycleModels(dto.getMotorcycleModels());
        oldMotorcycle.setPatio(patio);

        motorcycleRepository.save(oldMotorcycle);
        return ResponseEntity.ok(oldMotorcycle);
    }

    private Motorcycle getMotorcycle(Long id){
        return motorcycleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Motocicleta não encontrada"));
    }
}
