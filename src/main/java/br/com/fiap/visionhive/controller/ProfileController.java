package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.Forms.AssociarPatioForm;
import br.com.fiap.visionhive.dto.Forms.ContarPatiosForm;
import br.com.fiap.visionhive.dto.Forms.UpdateSituacaoForm;
import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.User;
import br.com.fiap.visionhive.repository.UserRepository;
import br.com.fiap.visionhive.services.MotorcycleProcedureService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;
    private final MotorcycleProcedureService procedureService;

    @GetMapping
    @Operation(summary = "Exibir perfil do usuário", description = "Exibe a página de perfil do usuário autenticado")
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("user", user);
        return "user/profile";
    }

    @PostMapping("/update-situacao")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateSituacao(@Valid @ModelAttribute UpdateSituacaoForm form,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            String errorMsg = result.getAllErrors().get(0).getDefaultMessage();
            model.addAttribute("result", new ProcedureResponse(null, "Erro de validação: " + errorMsg));
            return "fragments/procedure-result :: result";
        } else {
            ProcedureResponse resp = procedureService.atualizarSituacao(form.placa(), form.novaSituacao());
            model.addAttribute("result", resp);
        }
        return "fragments/procedure-result :: result";
    }

    @PostMapping("/associar-patio")
    @PreAuthorize("hasRole('ADMIN')")
    public String associarPatio(@Valid @ModelAttribute AssociarPatioForm form,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("result", new ProcedureResponse(null, "Erro: " + result.getAllErrors().get(0).getDefaultMessage()));
        } else {
            String mensagem = procedureService.associarPatio(form.placa(), form.patioId());
            model.addAttribute("result", new ProcedureResponse(null, mensagem));
        }
        return "fragments/procedure-result :: result";
    }

    @PostMapping("/contar-patios")
    @PreAuthorize("hasRole('ADMIN')")
    public String contarPatios(@ModelAttribute ContarPatiosForm form, Model model) {
        try {
            int total = procedureService.contarPatiosPorFilial(form.branchId());
            model.addAttribute("result", new ProcedureResponse(null,
                    "Filial ID " + form.branchId() + " possui " + total + " pátio(s)."));
        } catch (Exception e) {
            model.addAttribute("result", new ProcedureResponse(null, "Erro: " + e.getMessage()));
        }
        return "fragments/procedure-result :: result";
    }
}

