package net.ddns.varuzhan.demo.controller.manager.dashboard.classmaterials;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.TreeSet;

@Controller
public class ClassMaterialsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    public ClassMaterialsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
    }
/*
    @GetMapping("/manager/dashboard/classMaterials/{groupId}")
    public String loadGroupsPage(Model model, @PathVariable String groupId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        model.addAttribute("full_name", fullName);
        TreeSet<SubjectInfo> managerSubjects = new TreeSet<>();
        GroupInfo groupInfo=null;
        boolean isGroupCorrect = false;
        for(ManagersGroupsSubjects x : managersGroupsSubjectsService.getManagersGroupsUsersByManager(userService.getUserByEmail(authentication.getName()))){
            if(x.getGroupInfo().getId().equals(Integer.parseInt(groupId)))
            {
                isGroupCorrect=true;
                groupInfo=x.getGroupInfo();
                break;
            }
        }
        if(!isGroupCorrect) return "redirect:/error";
        for(ManagersGroupsSubjects x : managersGroupsSubjectsService.getManagersGroupsUsersByManager(userService.getUserByEmail(authentication.getName()))){
            if(x.getGroupInfo().getId().equals(Integer.parseInt(groupId)))
                managerSubjects.add(x.getSubjectInfo());
        }
        model.addAttribute("subjects",managerSubjects);
        model.addAttribute("groupNumber",groupInfo.getGroupNumber());
        return "manager/dashboard/managerDashboardClassMaterialsForGroup";
    }
*/
}
