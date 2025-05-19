package com.visionhive.visionhive.controller;

import com.visionhive.visionhive.dto.PatioDTO;
import com.visionhive.visionhive.model.Patio;
import com.visionhive.visionhive.repository.BranchRepository;
import com.visionhive.visionhive.repository.PatioRepository;
import com.visionhive.visionhive.specification.PatioSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

@RestController
@RequestMapping("patios")
@Slf4j
public class PatioController {

    public record PatioFilters (String nome, Long branchId){}

    @Autowired
    private PatioRepository repository;

    @Autowired
    private BranchRepository branchRepository;

    @GetMapping
    @Operation(summary = "Listar pátios", description = "Retorna um array com todos os pátios de uma filial")
    @Cacheable("patios")
    public Page<Patio> index(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Long branchId,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ){
        var filters = new PatioFilters(nome, branchId);
        var specification = PatioSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "patios", allEntries = true)
    @Operation(summary = "Inserir pátios", description = "Inserir um pátio novo", responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Patio> create(@RequestBody @Valid PatioDTO dto){
        log.info("Cadastrando pátio: " + dto.getNome());

        var branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        Patio patio = new Patio();
        patio.setNome(dto.getNome());
        patio.setBranch(branch);

        Patio saved = repository.save(patio);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar pátio", description = "Retorna o pátio buscado pelo ID")
    public ResponseEntity<Patio> get(@PathVariable Long id){
        log.info("Buscando pátio: " + id);
        return ResponseEntity.ok(getPatio(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar pátio", description = "Deleta o pátio escolhido")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deletando pátio: " + id);
        repository.delete(getPatio(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar pátio", description = "Atualizar o pátio")
    public ResponseEntity<Patio> update(@PathVariable Long id, @RequestBody @Valid PatioDTO dto){
        log.info("Atualizando pátio: " + id + " com " + dto);

        Patio oldPatio = getPatio(id);

        var branch = branchRepository.findById(dto.getBranchId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filial não encontrada"));

        oldPatio.setNome(dto.getNome());
        oldPatio.setBranch(branch);

        repository.save(oldPatio);

        return ResponseEntity.ok(oldPatio);
    }

    private Patio getPatio(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pátio não encontrado"));
    }
}
