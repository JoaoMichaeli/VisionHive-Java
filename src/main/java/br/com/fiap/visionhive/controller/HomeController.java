package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.services.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BranchService branchService;

    @GetMapping()
    public String index(Model model) {
        var branches = branchService.findAllBranches();
        model.addAttribute("branches", branches);
        return "index";
    }
}

