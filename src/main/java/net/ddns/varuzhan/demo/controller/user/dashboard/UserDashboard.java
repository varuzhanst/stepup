package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserDashboard {
    private final UserService userService;

    public UserDashboard(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/dashboard")
    public String loadUserDashboard(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("full_name",userService.getUserByEmail(authentication.getName()).getFirstName()
        +" " +userService.getUserByEmail(authentication.getName()).getMiddleName()
        +" "+ userService.getUserByEmail(authentication.getName()).getLastName());
        return "user/userDashboard";
 }
}
