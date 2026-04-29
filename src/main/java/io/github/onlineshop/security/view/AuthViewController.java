package io.github.onlineshop.security.view;

import io.github.onlineshop.security.api.dto.JwtRequest;
import io.github.onlineshop.security.api.dto.JwtResponse;
import io.github.onlineshop.security.api.dto.RegistrationResponse;
import io.github.onlineshop.security.domain.AuthenticationService;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.domain.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthViewController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @GetMapping("/login")
    public String loginPanel(Model model) {
        model.addAttribute("loginRequest", new JwtRequest(null, null));
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute(
            "registerRequest",
            new UserCreateRequest(null, null, null)
        );
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
        @ModelAttribute UserCreateRequest registerRequest,
        RedirectAttributes redirectAttributes
    ) {
        try {
            RegistrationResponse response = authenticationService.register(registerRequest);
            redirectAttributes.addFlashAttribute("message",
                "Регистрация успешна! Теперь вы можете войти.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка регистрации: " + e.getMessage());
            return "redirect:/auth/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/products";
    }
}
