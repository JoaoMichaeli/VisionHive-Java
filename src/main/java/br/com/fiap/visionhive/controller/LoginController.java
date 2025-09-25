package br.com.fiap.visionhive.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    @Operation(summary = "Página de login", description = "Exibe a página de login para os usuários")
    public String login() {
        return "login";
    }
}
