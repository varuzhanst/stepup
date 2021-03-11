package net.ddns.varuzhan.demo.controller.admin.dashboard.registration;


import net.ddns.varuzhan.demo.dto.AdminRegisteredPersonDto;
import net.ddns.varuzhan.demo.model.AdminRegisteredPerson;
import net.ddns.varuzhan.demo.service.AdminRegisteredPersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/registration")
public class NewUserManagerController {
    private final AdminRegisteredPersonService adminRegisteredPersonService;

    public NewUserManagerController(AdminRegisteredPersonService adminRegisteredPersonService) {
        this.adminRegisteredPersonService = adminRegisteredPersonService;
    }


    @GetMapping("/user")
    public String loadDashboardNewUserPage(Model model) {
        model.addAttribute("user", new AdminRegisteredPersonDto());
        return "admin/dashboard/newUser";
    }
    @GetMapping("/manager")
    public String loadDashboardNewManagerPage(Model model) {
        model.addAttribute("user", new AdminRegisteredPersonDto());
        return "admin/dashboard/newManager";
    }

    @PostMapping("/user")
    public String newUserRegistrationProcessing(@ModelAttribute("user") AdminRegisteredPersonDto adminRegisteredPersonDto) {
        if (adminRegisteredPersonService.isUserFoundByEmail(adminRegisteredPersonDto.getEmail())) {
            return "redirect:/admin/dashboard/users/new?error";
        } else {
            adminRegisteredPersonService.save(new AdminRegisteredPerson(
                    adminRegisteredPersonDto.getFirstName(),
                    adminRegisteredPersonDto.getLastName(),
                    adminRegisteredPersonDto.getMiddleName(),
                    adminRegisteredPersonDto.getEmail(),
                    "STUDENT",
                    false
            ));
            return "redirect:/admin/dashboard/registration/user?success";
        }
    }

    @PostMapping("/manager")
    public String newManagerRegistrationProcessing(@ModelAttribute("user") AdminRegisteredPersonDto adminRegisteredPersonDto) {
        if (adminRegisteredPersonService.isUserFoundByEmail(adminRegisteredPersonDto.getEmail())) {
            return "redirect:/admin/dashboard/registration/manager?error";
        } else {
            adminRegisteredPersonService.save(new AdminRegisteredPerson(
                    adminRegisteredPersonDto.getFirstName(),
                    adminRegisteredPersonDto.getLastName(),
                    adminRegisteredPersonDto.getMiddleName(),
                    adminRegisteredPersonDto.getEmail(),
                    "MANAGER",
                    false
            ));
            return "redirect:/admin/dashboard/registration/manager?success";
        }
    }



}
