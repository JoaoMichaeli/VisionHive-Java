package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.Branch;
import br.com.fiap.visionhive.services.BranchService;
import br.com.fiap.visionhive.services.MotorcycleService;
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
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final MotorcycleService motorcycleService;
    private final BranchService branchService;

    public record BranchFilters(String nome, String bairro, String cnpj) {}

    @GetMapping
    @Operation(summary = "Listar filiais", description = "Retorna um array com todas as filiais")
    public String index(@RequestParam(required = false) String nome,
                        @RequestParam(required = false) String bairro,
                        @RequestParam(required = false) String cnpj,
                        @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {

        var filters = new BranchFilters(nome, bairro, cnpj);
        var branchesPage = branchService.findAllWithFilters(filters, pageable);
        long totalFiltered = branchService.countWithFilters(filters);
        long totalBranches = branchService.countAllBranches();
        var motorcycles = motorcycleService.getAllMotorcycles();
        var motorcyclesByBranch = motorcycleService.countMotorcyclesByBranch();

        model.addAttribute("motorcycles", motorcycles);
        model.addAttribute("motorcycleByBranch", motorcyclesByBranch);
        model.addAttribute("branches", branchesPage);
        model.addAttribute("totalFiltered", totalFiltered);
        model.addAttribute("totalBranches", totalBranches);

        return "branch/index";
    }

    @GetMapping("/form")
    @Operation(summary = "Exibir formulário de cadastro", description = "Retorna o formulário para criar uma nova filial")
    public String form(Model model) {
        model.addAttribute("branch", new Branch());
        return "branch/form";
    }

    @PostMapping("/form")
    @Operation(summary = "Cadastrar filial", description = "Cadastra uma nova filial no sistema")
    public String create(@Valid Branch branch,
                         BindingResult result,
                         RedirectAttributes redirect) {

        if (result.hasErrors()) return "branch/form";

        branchService.save(branch);
        redirect.addFlashAttribute("message", "Filial cadastrada com sucesso!");
        return "redirect:/branch";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Detalhar filial", description = "Retorna os detalhes de uma filial específica pelo ID")
    public String detail(@PathVariable Long id, Model model) {
        var branch = branchService.findById(id);
        model.addAttribute("branch", branch);
        model.addAttribute("patios", branch.getPatios());
        return "branch/detail";
    }
}
