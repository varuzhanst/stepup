package net.ddns.varuzhan.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class AdminController {
    @GetMapping("/login")
    public String loadAdminLoginPage(){
        return "admin/login";
    }
}
