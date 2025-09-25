package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.Patio;
import br.com.fiap.visionhive.repository.MotorcycleRepository;
import br.com.fiap.visionhive.repository.PatioRepository;
import br.com.fiap.visionhive.services.BranchService;
import br.com.fiap.visionhive.services.PatioService;
import br.com.fiap.visionhive.specification.MotorcycleSpecification;
import br.com.fiap.visionhive.specification.PatioSpecification;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patio")
@RequiredArgsConstructor
public class PatioController {

    private final PatioService patioService;
    private final BranchService branchService;
    private final PatioRepository patioRepository;
    private final MotorcycleRepository motorcycleRepository;

    public record PatioFilters(String nome, String branchNome) {}

    @GetMapping("/form")
    @Operation(summary = "Exibir formulário de cadastro", description = "Mostra o formulário para cadastrar um novo pátio vinculado a uma filial")
    public String form(@RequestParam Long branchId, Model model) {
        var branch = branchService.findById(branchId);
        var patio = new Patio();
        patio.setBranch(branch);

        model.addAttribute("patio", patio);
        return "patio/form";
    }

    @PostMapping("/form")
    @Operation(summary = "Cadastrar pátio", description = "Salva um novo pátio no sistema")
    public String create(@Valid Patio patio, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "patio/form";

        patioService.save(patio);
        redirect.addFlashAttribute("message", "Pátio cadastrado com sucesso!");
        return "redirect:/patio/" + patio.getId();
    }


    @GetMapping()
    @Operation(summary = "Listar pátios", description = "Retorna um array com todos os pátios de uma filial")
    public String patios(@RequestParam(required = false) String nome,
                         @RequestParam(required = false) String branchNome,
                         @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                         Model model) {

        var filters = new PatioFilters(nome, branchNome);
        var spec = PatioSpecification.withFilters(filters);
        var patios = patioRepository.findAll(spec, pageable);
        model.addAttribute("patios", patios);
        return "patio/index";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar pátio", description = "Exibe os detalhes de um pátio e suas motocicletas vinculadas")
    public String detail(
            @PathVariable Long id,
            @RequestParam(required = false) String placa,
            @RequestParam(required = false) String chassi,
            @RequestParam(required = false) String numeracaoMotor,
            @RequestParam(required = false) String situacao,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

        var patio = patioService.findById(id);

        var filters = new MotorcycleController.MotorcycleFilters(placa, chassi, numeracaoMotor, situacao);

        var spec = MotorcycleSpecification.withFilters(filters)
                .and((root, query, cb) -> cb.equal(root.get("patio").get("id"), id));

        var motorcycles = motorcycleRepository.findAll(spec, pageable);

        model.addAttribute("patio", patio);
        model.addAttribute("motorcycles", motorcycles);

        return "patio/detail";
    }

}
