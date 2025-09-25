package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import br.com.fiap.visionhive.services.MotorcycleService;
import br.com.fiap.visionhive.services.PatioService;
import br.com.fiap.visionhive.specification.MotorcycleSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motorcycle")
@RequiredArgsConstructor
public class MotorcycleController {

    private final MotorcycleService motorcycleService;
    private final PatioService patioService;
    private final MotorcycleRepository motorcycleRepository;

    public record MotorcycleFilters (String placa, String chassi, String numeracaoMotor, String situacao){}

    @GetMapping
    @Operation(summary = "Listar todas as motocicletas com filtros e paginação", description = "Retorna uma lista paginada de motocicletas com filtros opcionais")
    public String index(
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String numeracaoMotor,
            @RequestParam(required = false) String situacao,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        var filters = new MotorcycleFilters(placa, chassi, numeracaoMotor, situacao);
        var spec = MotorcycleSpecification.withFilters(filters);

        Page<Motorcycle> motorcycles = motorcycleRepository.findAll(spec, pageable);

        model.addAttribute("motorcycles", motorcycles.getContent());
        model.addAttribute("page", motorcycles);
        return "motorcycle/index";
    }

    @GetMapping("/form")
    @Operation(summary = "Exibir formulário de cadastro", description = "Mostra o formulário para cadastrar uma nova motocicleta")
    public String form(@RequestParam(required = false) Long patioId, Model model) {
        var moto = new Motorcycle();
        if (patioId != null) {
            var patio = patioService.findById(patioId);
            moto.setPatio(patio);
        }
        model.addAttribute("motorcycle", moto);
        return "motorcycle/form";
    }

    @PostMapping("/form")
    @Operation(summary = "Cadastrar motocicleta", description = "Salva uma nova motocicleta no sistema")
    public String create(@Valid Motorcycle motorcycle, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "motorcycle/form";

        motorcycleService.save(motorcycle);
        redirect.addFlashAttribute("message", "Moto cadastrada com sucesso!");

        if (motorcycle.getPatio() != null) {
            return "redirect:/patio/" + motorcycle.getPatio().getId();
        }

        return "redirect:/motorcycle";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar motocicleta", description = "Exibe os detalhes de uma motocicleta específica")
    public String detail(@PathVariable Long id, Model model) {
        var motorcycle = motorcycleService.findById(id);
        model.addAttribute("motorcycle", motorcycle);
        return "motorcycle/detail";
    }

    @GetMapping("/edit/{id}")
    @Operation(summary = "Exibir formulário de edição", description = "Mostra o formulário para editar uma motocicleta existente")
    public String edit(@PathVariable Long id, Model model) {
        var motorcycle = motorcycleService.findById(id);
        model.addAttribute("motorcycle", motorcycle);

        var patios = patioService.findAllPatios();
        model.addAttribute("patios", patios);

        return "motorcycle/edit";
    }

    @PostMapping("/edit/{id}")
    @Operation(summary = "Atualizar motocicleta", description = "Atualiza os dados de uma motocicleta existente")
    public String update(
            @PathVariable Long id,
            @Valid Motorcycle motorcycle,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirect
    ) {
        if (bindingResult.hasErrors()) {
            var patios = patioService.findAllPatios();
            model.addAttribute("patios", patios);
            return "motorcycle/edit";
        }

        motorcycle.setId(id);
        motorcycleService.save(motorcycle);

        redirect.addFlashAttribute("message", "Moto atualizada com sucesso!");

        return "redirect:/motorcycle";
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Deletar motocicleta", description = "Remove uma motocicleta do sistema")
    public String delete(@PathVariable Long id) {
        motorcycleService.deleteById(id);
        return "redirect:/motorcycle";
    }

}
