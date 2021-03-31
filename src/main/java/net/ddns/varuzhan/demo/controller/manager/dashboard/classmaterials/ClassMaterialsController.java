package net.ddns.varuzhan.demo.controller.manager.dashboard.classmaterials;

import net.ddns.varuzhan.demo.dto.ClassMaterialAdditionDto;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.TreeSet;


@Controller
public class ClassMaterialsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final SubjectInfoService subjectInfoService;
    private final ClassMaterialService classMaterialService;
    public ClassMaterialsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, ClassMaterialService classMaterialService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.classMaterialService = classMaterialService;
    }

    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects")
    public String loadGroupsPage(Model model,@PathVariable String groupId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName()+ " " + user.getMiddleName()+ " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        if (groupInfoById == null) {
            return "redirect:/error";
        }
        HashSet<ManagersGroupsSubjects> subjects = (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagersGroupsAndSubjectsByGroupAndSubject(groupInfoById, user);
        TreeSet<SubjectInfo> subjectsOfGroup = new TreeSet<>();
        for(ManagersGroupsSubjects x: subjects){
            subjectsOfGroup.add(x.getSubjectInfo());
        }
        TreeSet<GroupInfo> groupsOfManager = new TreeSet<>();
        HashSet<ManagersGroupsSubjects> groupsAndSubjectsOfManager = (HashSet<ManagersGroupsSubjects>)managersGroupsSubjectsService.getManagersGroupAndSubjectInfos(user);
        for(ManagersGroupsSubjects x: groupsAndSubjectsOfManager){
            groupsOfManager.add(x.getGroupInfo());
        }
        model.addAttribute("group",groupInfoById);
        model.addAttribute("subjects",subjectsOfGroup);
        return "manager/dashboard/classMaterials/classMaterials_subjectsOfGroup";
    }
    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects/{subjectId}")
    public String loadGroupsPage(Model model,@PathVariable String groupId,@PathVariable String subjectId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user=userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName()+ " " + user.getMiddleName()+ " " + user.getLastName();
        model.addAttribute("full_name", fullName);

        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        SubjectInfo subjectInfoById = subjectInfoService.getSubjectInfoById(subjectId);

        if (groupInfoById == null||subjectInfoById==null) {
            return "redirect:/error";
        }
        ManagersGroupsSubjects managerGroupSubject = managersGroupsSubjectsService.getManagerGroupSubjectByGroupManagerSubject(groupInfoById,user,subjectInfoById);
        if (managerGroupSubject==null) {
            return "redirect:/error";
        }
        TreeSet<ClassMaterial> classMaterials =(TreeSet<ClassMaterial>)classMaterialService.getMaterialsByManagerGroupSubject(managerGroupSubject);

        model.addAttribute("group",groupInfoById);
        model.addAttribute("subject",subjectInfoById);
        model.addAttribute("newMaterial", new ClassMaterialAdditionDto());
        model.addAttribute("classMaterials",classMaterials);
        return "manager/dashboard/classMaterials/classMaterialsView";
    }
}
