package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.BranchDTO;
import br.com.fiap.visionhive.model.Branch;
import br.com.fiap.visionhive.repository.BranchRepository;
import br.com.fiap.visionhive.services.BranchService;
import br.com.fiap.visionhive.specification.BranchSpecification;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/branch")
@RequiredArgsConstructor
public class BranchController {

    private final BranchRepository branchRepository;
    private final BranchService branchService;

    public record BranchFilters(String nome, String bairro, String cnpj) {}

    @GetMapping
    public String index(@RequestParam(required = false) String nome,
                        @RequestParam(required = false) String bairro,
                        @RequestParam(required = false) String cnpj,
                        Model model) {

        var filters = new BranchFilters(nome, bairro, cnpj);
        var spec = BranchSpecification.withFilters(filters);
        var branches = branchRepository.findAll(spec);

        model.addAttribute("branches", branches);
        return "branch/index";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("branch", new Branch());
        return "branch/form";
    }

    @PostMapping("/form")
    public String create(@Valid Branch branch,
                         BindingResult result,
                         RedirectAttributes redirect) {

        if (result.hasErrors()) return "branch/form";

        branchService.save(branch);
        redirect.addFlashAttribute("message", "Filial cadastrada com sucesso!");
        return "redirect:/branch";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var branch = branchService.findById(id);
        model.addAttribute("branch", branch);
        model.addAttribute("patios", branch.getPatios());
        return "branch/detail";
    }
}
