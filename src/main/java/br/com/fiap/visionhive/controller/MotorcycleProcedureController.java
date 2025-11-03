package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.Procedure.ProcedureResponse;
import br.com.fiap.visionhive.model.Motorcycle;
import br.com.fiap.visionhive.services.MotorcycleProcedureService;
import br.com.fiap.visionhive.services.MotorcycleService;
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

    @GetMapping("/atualizar-situacao/{placa}")
    public String formAtualizarSituacao(@PathVariable String placa, Model model) {
        Motorcycle moto = (Motorcycle) motorcycleService.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));
        model.addAttribute("moto", moto);
        return "motorcycle/atualizar-situacao";
    }

    @PostMapping("/atualizar-situacao/{placa}")
    public String executarAtualizarSituacao(
            @PathVariable String placa,
            @RequestParam String novaSituacao,
            Model model) {

        Motorcycle moto = (Motorcycle) motorcycleService.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Moto com placa " + placa + " não encontrada"));

        ProcedureResponse resp = procedureService.atualizarSituacao(String.valueOf(moto.getId()), novaSituacao);

        model.addAttribute("json", resp.json());
        model.addAttribute("mensagem", resp.mensagem());
        model.addAttribute("placa", placa);

        return "motorcycle/resultado-procedure";
    }
}
