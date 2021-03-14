package net.ddns.varuzhan.demo.controller.admin.dashboard.registration;


import net.ddns.varuzhan.demo.dto.AdminRegisteredPersonDto;
import net.ddns.varuzhan.demo.model.UserData;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.service.implementation.UserDataServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/dashboard/registration")
public class NewUserManagerController {
    private final UserDataServiceImpl userDataServiceImpl;

    public NewUserManagerController(UserDataServiceImpl userDataServiceImpl) {
        this.userDataServiceImpl = userDataServiceImpl;
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
        if (userDataServiceImpl.findUserDataByEmail(adminRegisteredPersonDto.getEmail())!=null) {
            return "redirect:/admin/dashboard/registration/user?error";
        } else {
            userDataServiceImpl.save(new UserData(
                    adminRegisteredPersonDto.getFirstName(),
                    adminRegisteredPersonDto.getLastName(),
                    adminRegisteredPersonDto.getMiddleName(),
                    adminRegisteredPersonDto.getEmail(),
                    Role.USER,
                    false
            ));
            return "redirect:/admin/dashboard/registration/user?success";
        }
    }

    @PostMapping("/manager")
    public String newManagerRegistrationProcessing(@ModelAttribute("user") AdminRegisteredPersonDto adminRegisteredPersonDto) {
        if (userDataServiceImpl.findUserDataByEmail(adminRegisteredPersonDto.getEmail())!=null) {
            return "redirect:/admin/dashboard/registration/manager?error";
        } else {
            userDataServiceImpl.save(new UserData(
                    adminRegisteredPersonDto.getFirstName(),
                    adminRegisteredPersonDto.getLastName(),
                    adminRegisteredPersonDto.getMiddleName(),
                    adminRegisteredPersonDto.getEmail(),
                    Role.MANAGER,
                    false
            ));
            return "redirect:/admin/dashboard/registration/manager?success";
        }
    }



}
