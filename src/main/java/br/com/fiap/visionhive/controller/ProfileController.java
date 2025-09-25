package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.User;
import br.com.fiap.visionhive.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    @GetMapping
    public String profile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        model.addAttribute("user", user);
        return "user/profile";
    }
}

