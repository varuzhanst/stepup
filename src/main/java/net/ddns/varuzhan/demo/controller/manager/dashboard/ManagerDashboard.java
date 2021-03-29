package net.ddns.varuzhan.demo.controller.manager.dashboard;

import net.ddns.varuzhan.demo.dto.ClassMaterialAdditionDto;
import net.ddns.varuzhan.demo.fileupload.FileUploadUtil;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.FileService;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.UUID;

@Controller
@RequestMapping("/manager/dashboard")
public class ManagerDashboard {
    private final UserService userService;
    private final FileService fileService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;

    public ManagerDashboard(UserService userService, FileService fileService, ManagersGroupsSubjectsService managersGroupsSubjectsService) {
        this.userService = userService;
        this.fileService = fileService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
    }

    @GetMapping
    public String loadManagerDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        model.addAttribute("full_name", fullName);
        HashSet<ClassMaterial> materials = new HashSet<>();
        TreeSet<GroupInfo> managerGroups = new TreeSet<>();
        for(ManagersGroupsSubjects x : managersGroupsSubjectsService.getManagersGroupsUsersByManager(userService.getUserByEmail(authentication.getName()))){
            managerGroups.add(x.getGroupInfo());
        }
        model.addAttribute("groups", managerGroups);
        return "manager/dashboard/managerDashboard";
    }

}
