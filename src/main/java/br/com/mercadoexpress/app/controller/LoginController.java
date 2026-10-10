
package br.com.mercadoexpress.app.controller;

import br.com.mercadoexpress.app.entity.User;
import br.com.mercadoexpress.app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String cadastrar(
            @ModelAttribute User user,
            Model model) {

        try {
            userService.cadastrar(user);
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "signup";
        }
    }
}
