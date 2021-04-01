package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.service.prototype.ClassMaterialService;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class UserDashboard {
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final ClassMaterialService classMaterialService;
    public UserDashboard(UserService userService, UserGroupInfoService userGroupInfoService, ManagersGroupsSubjectsService managersGroupsSubjectsService, ClassMaterialService classMaterialService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.classMaterialService = classMaterialService;
    }

    @RequestMapping("/user/dashboard")
    public String loadUserDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        UserGroupInfo groupInfoByUser = userGroupInfoService.getGroupInfoByUser(userService.getUserByEmail(authentication.getName()));
        if(groupInfoByUser==null) return "redirect:/login?noGroup";
        HashSet<ManagersGroupsSubjects> userAvailableManagersSubjects= (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagerSubjectByGroup(groupInfoByUser.getGroupInfo());
        TreeSet<ClassMaterial> userClassMaterials = new TreeSet<>();
        for(ManagersGroupsSubjects x : userAvailableManagersSubjects){
            Set<ClassMaterial> setOfMaterials = classMaterialService.getMaterialsByManagerGroupSubject(x);
            userClassMaterials.addAll(setOfMaterials);
        }
        model.addAttribute("userMaterials",userClassMaterials);
        String groupNumber = " (" + groupInfoByUser.getGroupInfo().getGroupNumber() + ")";
        model.addAttribute("full_name_group_number", fullName + groupNumber );
        return "user/dashboard/userDashboard";
    }
}
