package net.ddns.varuzhan.demo.controller.manager.dashboard;

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
        TreeSet<GroupInfo> managerGroups = new TreeSet<>();
        TreeSet<SubjectInfo> managerSubjects = new TreeSet<>();
        for(ManagersGroupsSubjects x : managersGroupsSubjectsService.getManagersGroupsUsersByManager(userService.getUserByEmail(authentication.getName()))){
            managerGroups.add(x.getGroupInfo());
            managerSubjects.add(x.getSubjectInfo());
        }
        model.addAttribute("groups", managerGroups);
        model.addAttribute("subjects", managerSubjects);
        return "manager/dashboard/managerDashboard";
    }

    @PostMapping
    public String saveMaterial(@RequestParam("material") MultipartFile multipartFile) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ClassMaterial material = new ClassMaterial();
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        File file = new File();
        file.setFileToken(UUID.randomUUID().toString());
        file.setAddedBy(userService.getUserByEmail(authentication.getName()));
        file.setAddedAt(LocalDateTime.now());
        String uploadDir = "users_files/" + userService.getUserByEmail(authentication.getName()).getId();
        file.setFilePath(uploadDir + "/" + fileName);
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            fileService.saveFile(file);
        } catch (IOException e) {
            return "redirect:/error";
        }
        return "redirect:/manager/dashboard";
    }
}
