package net.ddns.varuzhan.demo.controller.admin.dashboard.registration;


import net.ddns.varuzhan.demo.dto.UserRegistrationDto;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.service.implementation.UserServiceImpl;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/registration")
public class NewUserManagerController {
    private final UserService userService;

    public NewUserManagerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String loadDashboardNewUserPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "admin/dashboard/newUser";
    }
    @GetMapping("/manager")
    public String loadDashboardNewManagerPage(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "admin/dashboard/newManager";
    }

    @PostMapping("/user")
    public String newUserRegistrationProcessing(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
        if (userService.getUserByEmail(userRegistrationDto.getEmail())!=null) {
            return "redirect:/admin/dashboard/registration/user?error";
        } else {
            User user = new User();
            user.setFirstName(userRegistrationDto.getFirstName());
            user.setLastName(userRegistrationDto.getLastName());
            user.setMiddleName(userRegistrationDto.getMiddleName());
            user.setEmail(userRegistrationDto.getEmail());
            user.setPassword(null);
            user.setRole(Role.USER);
            user.setEnabled(false);
            user.setLocked(false);
            userService.save(user);
            return "redirect:/admin/dashboard/registration/user?success";
        }
    }

    @PostMapping("/manager")
    public String newManagerRegistrationProcessing(@ModelAttribute("user") UserRegistrationDto userRegistrationDto) {
        if (userService.getUserByEmail(userRegistrationDto.getEmail())!=null) {
            return "redirect:/admin/dashboard/registration/manager?error";
        } else {
            User user = new User();
            user.setFirstName(userRegistrationDto.getFirstName());
            user.setLastName(userRegistrationDto.getLastName());
            user.setMiddleName(userRegistrationDto.getMiddleName());
            user.setEmail(userRegistrationDto.getEmail());
            user.setPassword(null);
            user.setRole(Role.MANAGER);
            user.setEnabled(false);
            user.setLocked(false);
            userService.save(user);
            return "redirect:/admin/dashboard/registration/manager?success";
        }
    }



}
