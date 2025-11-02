package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.services.MotorcycleProcedureService;
import br.com.fiap.visionhive.services.MotorcycleService;
import br.com.fiap.visionhive.services.PatioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/motorcycles/procedures")
@RequiredArgsConstructor
public class MotorcycleProcedureController {

    private final MotorcycleProcedureService procedureService;
    private final MotorcycleService motorcycleService;
    private final PatioService patioService;

    @GetMapping("/atualizar-situacao/{id}")
    public String formAtualizarSituacao(@PathVariable Long id, Model model) {
        Motorcycle moto = motorcycleService.findById(id);
        model.addAttribute("moto", moto);
        return "motorcycle/atualizar-situacao";
    }

    @PostMapping("/atualizar-situacao/{id}")
    public String executarAtualizarSituacao(
            @PathVariable Long id,
            @RequestParam String novaSituacao,
            Model model) {

        ProcedureResponse resp = procedureService.atualizarSituacao(id, novaSituacao);

        model.addAttribute("json", resp.json());
        model.addAttribute("mensagem", resp.mensagem());
        model.addAttribute("motoId", id);

        return "motorcycle/resultado-procedure";
    }
}
