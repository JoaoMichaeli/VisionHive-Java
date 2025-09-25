package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.dto.CreateUserDTO;
import br.com.fiap.visionhive.model.Role;
import br.com.fiap.visionhive.model.User;
import br.com.fiap.visionhive.repository.UserRepository;
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
    public String createOperatorForm(Model model) {
        model.addAttribute("createUserDTO", new CreateUserDTO());
        return "admin/create-operator";
    }

    @PostMapping("/admin/create-operator")
    public String createOperator(
            @Valid @ModelAttribute("createUserDTO") CreateUserDTO createUserDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "admin/create-operator";
        }

        User user = User.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(Set.of(Role.ROLE_OPERADOR))
                .build();

        userRepository.save(user);

        return "redirect:/admin/users";
    }
}
