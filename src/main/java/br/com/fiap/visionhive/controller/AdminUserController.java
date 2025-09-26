package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.CreateUserDTO;
import br.com.fiap.visionhive.model.Role;
import br.com.fiap.visionhive.model.User;
import br.com.fiap.visionhive.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/admin/create-operator")
    @Operation(summary = "Exibir formulário de criação de operador", description = "Retorna o formulário para criar um novo usuário operador")
    public String createOperatorForm(Model model) {
        model.addAttribute("createUserDTO", new CreateUserDTO());
        return "admin/create-operator";
    }

    @PostMapping("/admin/create-operator")
    @Operation(summary = "Criar usuário", description = "Cria um novo usuário no sistema com a função escolhida")
    public String createUser(
            @Valid @ModelAttribute("createUserDTO") CreateUserDTO createUserDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/create-operator";
        }

        User user = User.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(Set.of(createUserDTO.getRole() != null ? createUserDTO.getRole() : Role.ROLE_OPERADOR))
                .build();

        userRepository.save(user);

        return "redirect:/admin/users";
    }
}
