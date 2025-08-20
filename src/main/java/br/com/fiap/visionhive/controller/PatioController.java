package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.Patio;
import br.com.fiap.visionhive.services.BranchService;
import br.com.fiap.visionhive.services.PatioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/form")
    public String form(@RequestParam Long branchId, Model model) {
        var branch = branchService.findById(branchId);
        var patio = new Patio();
        patio.setBranch(branch);

        model.addAttribute("patio", patio);
        return "patio/form";
    }

    @PostMapping("/form")
    public String create(@Valid Patio patio, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) return "patio/form";

        patioService.save(patio);
        redirect.addFlashAttribute("message", "Pátio cadastrado com sucesso!");
        return "redirect:/patio/" + patio.getId();
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        var patio = patioService.findById(id);
        model.addAttribute("patio", patio);
        model.addAttribute("motorcycles", patio.getMotorcycles());
        return "patio/detail";
    }
}
