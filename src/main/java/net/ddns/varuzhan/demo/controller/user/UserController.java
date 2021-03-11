package net.ddns.varuzhan.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController {
    @GetMapping("/login")
    public String loadUserLoginPage() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String loadUserRegistrationView() {
        return "user/registration";
    }
}
