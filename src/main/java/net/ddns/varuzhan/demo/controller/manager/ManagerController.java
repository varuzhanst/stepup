package net.ddns.varuzhan.demo.controller.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager/")
public class ManagerController {
    @GetMapping("/login")
    public String loadManagerLoginView(){
        return "manager/login";
    }
    @GetMapping("/registration")
    public String loadManagerRegistrationView(){
        return "manager/registration";
    }
}
