package net.ddns.varuzhan.demo.controller.admin.dashboard.view;

import net.ddns.varuzhan.demo.service.AdminRegisteredPersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/dashboard/all")
public class AllUsersManagers {
    private final AdminRegisteredPersonService adminRegisteredPersonService;

    public AllUsersManagers(AdminRegisteredPersonService adminRegisteredPersonService) {
        this.adminRegisteredPersonService = adminRegisteredPersonService;
    }
    @GetMapping("/users")
    public String getStudents(Model model){
        model.addAttribute("users",adminRegisteredPersonService.getAllAdminRegisteredUsers());
        return "admin/dashboard/allUser";
    }

    @GetMapping("/managers")
    public String getManagers(Model model){
        model.addAttribute("managers",adminRegisteredPersonService.getAllAdminRegisteredManagers());
        return "admin/dashboard/allManager";
    }
}
