package ru.grigorii.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.UserService;

/**
 * Контроллер авторизации
 */
@Controller
public class AuthController
{
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", UserDto.nullDto());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserDto user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage()
    {
        return "login";
    }
}
