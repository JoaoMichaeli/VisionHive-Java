package br.com.fiap.visionhive.controller;

import br.com.fiap.visionhive.model.Role;
import br.com.fiap.visionhive.model.User;
import br.com.fiap.visionhive.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public String createUser(@Valid User user,
                             @RequestParam(required = false, name = "selectedRoles") Set<Role> selectedRoles) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (selectedRoles != null && !selectedRoles.isEmpty()) {
            user.setRoles(selectedRoles);
        } else {
            user.setRoles(Set.of(Role.ROLE_OPERADOR));
        }

        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/user-list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }
}
