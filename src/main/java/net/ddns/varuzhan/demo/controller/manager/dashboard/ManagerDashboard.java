package net.ddns.varuzhan.demo.controller.manager.dashboard;


import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.FileService;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.TreeSet;


@Controller
@RequestMapping()
public class ManagerDashboard {
    private final UserService userService;
    private final FileService fileService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;

    public ManagerDashboard(UserService userService, FileService fileService, ManagersGroupsSubjectsService managersGroupsSubjectsService) {
        this.userService = userService;
        this.fileService = fileService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
    }

    @GetMapping("/manager/dashboard")
    public String loadGroupsPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        TreeSet<GroupInfo> groupsOfManager = new TreeSet<>();
        HashSet<ManagersGroupsSubjects> groupsAndSubjectsOfManager = (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagersGroupAndSubjectInfos(user);
        for (ManagersGroupsSubjects x : groupsAndSubjectsOfManager) {
            groupsOfManager.add(x.getGroupInfo());
        }
        model.addAttribute("groupsOfManager", groupsOfManager);
        return "manager/dashboard/managerDashboard";
    }

}
