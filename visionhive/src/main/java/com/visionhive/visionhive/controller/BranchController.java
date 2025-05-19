package com.visionhive.visionhive.controller;

import com.visionhive.visionhive.model.Branch;
import com.visionhive.visionhive.repository.BranchRepository;
import com.visionhive.visionhive.specification.BranchSpecification;
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

@RestController
@RequestMapping("branchs")
@Slf4j
public class BranchController {

    public record BranchFilters (String nome, String bairro, String cnpj){}

    @Autowired
    private BranchRepository repository;

    @GetMapping
    @Operation(summary = "Listar filiais", description = "Retorna um array com todas as filiais")
    @Cacheable("branchs")
    public Page<Branch> index(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String cnpj,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable
    ){
        var filters = new BranchFilters(nome, bairro, cnpj);
        var specification = BranchSpecification.withFilters(filters);
        return repository.findAll(specification, pageable);
    }

    @PostMapping
    @CacheEvict(value = "branchs", allEntries = true)
    @Operation(summary = "Inserir filiais", description = "Inserir uma filial nova", responses = @ApiResponse(responseCode = "400", description = "Falha na validação"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public Branch create(@RequestBody @Valid Branch branch){
        log.info("Cadastrando filial: " + branch.getNome());
        return repository.save(branch);
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar filial", description = "Retorna a filial buscada pelo ID")
    public ResponseEntity<Branch> get(@PathVariable Long id){
        log.info("Buscando filial: " + id);
        return ResponseEntity.ok(getBranch(id));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar filial", description = "Deletar a filial escolhida")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        log.info("Deletando filial: " + id);
        repository.delete(getBranch(id));
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualizar filial", description = "Atualizar a filial")
    public ResponseEntity<Branch> update(@PathVariable Long id, @RequestBody @Valid Branch branch){
        log.info("Atualizando filial: " + id + " com " + branch);
        var oldBranch = getBranch(id);
        BeanUtils.copyProperties(branch, oldBranch, "id");
        repository.save(oldBranch);
        return ResponseEntity.ok(oldBranch);
    }

    private Branch getBranch(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filial não encontrada"));
    }
}
