package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.dto.SubjectsFilterDto;
import net.ddns.varuzhan.demo.dto.SubjectsIDsForFilterDto;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    private final AssignmentService assignmentService;
    private final SubjectInfoService subjectInfoService;
    public UserDashboard(UserService userService, UserGroupInfoService userGroupInfoService, ManagersGroupsSubjectsService managersGroupsSubjectsService, ClassMaterialService classMaterialService, AssignmentService assignmentService, SubjectInfoService subjectInfoService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.classMaterialService = classMaterialService;
        this.assignmentService = assignmentService;
        this.subjectInfoService = subjectInfoService;
    }

    @RequestMapping("/user/dashboard")
    public String loadUserDashboard(Model model, @ModelAttribute("selectedSubject") SubjectsFilterDto subjectsFilterDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        UserGroupInfo groupInfoByUser = userGroupInfoService.getGroupInfoByUser(userService.getUserByEmail(authentication.getName()));
        if(groupInfoByUser==null) return "redirect:/login?noGroup";
        HashSet<ManagersGroupsSubjects> userAvailableManagersSubjects= (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagerSubjectByGroup(groupInfoByUser.getGroupInfo());
        TreeSet<ClassMaterial> userClassMaterials = new TreeSet<>();
        TreeSet<Assignment> userAssignments = new TreeSet<>();
        TreeSet<SubjectsIDsForFilterDto> userAvailableSubjects = new TreeSet<>();
        userAvailableSubjects.add(new SubjectsIDsForFilterDto("-1","Բոլոր առարկաները"));
        for(ManagersGroupsSubjects x : userAvailableManagersSubjects){
            Set<ClassMaterial> setOfMaterials = classMaterialService.getMaterialsByManagerGroupSubject(x);
            Set<Assignment> setOfAssignments = assignmentService.getAssignmentsByManagerGroupSubject(x);
            userClassMaterials.addAll(setOfMaterials);
            userAssignments.addAll(setOfAssignments);
            userAvailableSubjects.add(new SubjectsIDsForFilterDto(x.getSubjectInfo().getId().toString(),x.getSubjectInfo().getSubjectName()));
        }

        if(subjectsFilterDto.getSubjectId() == null || subjectsFilterDto.getSubjectId().equals("-1")){
            subjectsFilterDto.setSubjectId("-1");
            model.addAttribute("userMaterials",userClassMaterials);
            model.addAttribute("availSubjects",userAvailableSubjects);
            model.addAttribute("userAssignments",userAssignments);
            model.addAttribute("subjectFilter",subjectsFilterDto);
        }
        else{
            userClassMaterials.clear();
            for(ManagersGroupsSubjects x : userAvailableManagersSubjects){
                Set<Assignment> setOfAssignments = assignmentService.getAssignmentsByManagerGroupSubject(x);
                userAssignments.addAll(setOfAssignments);
                if(x.getSubjectInfo().equals(subjectInfoService.getSubjectInfoById(subjectsFilterDto.getSubjectId()))){
                    Set<ClassMaterial> setOfMaterials = classMaterialService.getMaterialsByManagerGroupSubject(x);
                    userClassMaterials.addAll(setOfMaterials);
                }
                userAvailableSubjects.add(new SubjectsIDsForFilterDto(x.getSubjectInfo().getId().toString(),x.getSubjectInfo().getSubjectName()));
                model.addAttribute("userMaterials",userClassMaterials);
                model.addAttribute("availSubjects",userAvailableSubjects);
                model.addAttribute("subjectFilter",subjectsFilterDto);
                model.addAttribute("userAssignments",userAssignments);
            }
        }
        String groupNumber = " (" + groupInfoByUser.getGroupInfo().getGroupNumber() + ")";
        model.addAttribute("full_name_group_number", fullName + groupNumber );
        return "user/dashboard/userDashboard";
    }


}
