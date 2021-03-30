package net.ddns.varuzhan.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/login")
public class GlobalLoginPage {
    @GetMapping
    public String loadLoginPage(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));
        boolean hasManagerRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("MANAGER"));
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        if(!hasUserRole&&!hasAdminRole&&!hasManagerRole){
            return "login";
        }
        else return successRedirect();
    }

    @GetMapping("/redirect")
    public String  successRedirect(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"));
        boolean hasManagerRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("MANAGER"));
        boolean hasAdminRole = authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"));
        if(hasUserRole) return "redirect:/user/dashboard";
        else if (hasManagerRole) return "redirect:/manager/dashboard";
        else if (hasAdminRole) return "redirect:/admin/dashboard";
        else return "redirect:/login";
    }
}
