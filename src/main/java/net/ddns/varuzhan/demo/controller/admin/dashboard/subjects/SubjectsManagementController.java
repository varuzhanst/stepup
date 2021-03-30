package net.ddns.varuzhan.demo.controller.admin.dashboard.subjects;

import net.ddns.varuzhan.demo.dto.NewManagerSubjectDto;
import net.ddns.varuzhan.demo.dto.NewSubjectDto;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping
public class SubjectsManagementController {
    private final SubjectInfoService subjectInfoService;
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final GroupInfoService groupInfoService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;

    public SubjectsManagementController(SubjectInfoService subjectInfoService, UserService userService, UserGroupInfoService userGroupInfoService, GroupInfoService groupInfoService, ManagersGroupsSubjectsService managersGroupsSubjectsService) {
        this.subjectInfoService = subjectInfoService;
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.groupInfoService = groupInfoService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
    }

    @PostMapping("admin/dashboard/subjects/new")
    public String newGroup(@ModelAttribute("newSubject") NewSubjectDto newSubjectDto) {
        if (subjectInfoService.getSubjectInfoBySubjectName(newSubjectDto.getSubjectName()) != null) {
            return "redirect:/admin/dashboard?subjectAlreadyExists";
        } else {
            SubjectInfo subjectInfo = new SubjectInfo();
            subjectInfo.setSubjectName(newSubjectDto.getSubjectName());
            subjectInfoService.save(subjectInfo);
            return "redirect:/admin/dashboard?subjectAdditionSuccess";
        }
    }

    @GetMapping("admin/dashboard/groups/{groupId}/managers/{userId}/subjects")
    public String removeUserFromGroup(@PathVariable String groupId, @PathVariable String userId, Model model) {
        User userById = userService.getUserById(userId);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        if (userById == null || groupInfoById == null) {
            return "redirect:/error";
        }
        UserGroupInfo userGroupInfo = userGroupInfoService.getUserGroupInfoByGroupInfoAndUser(groupInfoById, userById);
        if (userGroupInfo == null) {
            return "redirect:/error";
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                    + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                    + " " + userService.getUserByEmail(authentication.getName()).getLastName();
            model.addAttribute("full_name", fullName);
            model.addAttribute("groupNumber", groupInfoById.getGroupNumber());
            model.addAttribute("user",userById.getFirstName()+" " + userById.getLastName()+" " + userById.getMiddleName());
            Set<ManagersGroupsSubjects> subjects = managersGroupsSubjectsService.getManagersGroupsAndSubjectsByGroupAndSubject(groupInfoById, userById);
            model.addAttribute("subjects",subjects);
            model.addAttribute("newManagerSubject",new NewManagerSubjectDto());
            model.addAttribute("eligibleSubjects",managersGroupsSubjectsService.getSubjectsWithoutManagerAndGroupInfo(userById,groupInfoById));
            return "admin/dashboard/subject/managerSubjects";
        }

    }

    @PostMapping("admin/dashboard/groups/{groupId}/managers/{userId}/subjects")
    public String removeUserFromGroup(@PathVariable String groupId, @PathVariable String userId, @ModelAttribute("newManagerSubject")NewManagerSubjectDto newManagerSubjectDto) {
        User userById = userService.getUserById(userId);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        SubjectInfo subjectInfoById = subjectInfoService.getSubjectInfoById(newManagerSubjectDto.getSubjectId());
        if (userById == null || groupInfoById == null || subjectInfoById ==null) {
            return "redirect:/error";
        }
        UserGroupInfo userGroupInfo = userGroupInfoService.getUserGroupInfoByGroupInfoAndUser(groupInfoById, userById);
        if (userGroupInfo == null) {
            return "redirect:/error";
        } else {
            ManagersGroupsSubjects managersGroupsSubjects = new ManagersGroupsSubjects();
            managersGroupsSubjects.setGroupInfo(groupInfoById);
            managersGroupsSubjects.setSubjectInfo(subjectInfoById);
            managersGroupsSubjects.setUser(userById);
            managersGroupsSubjectsService.save(managersGroupsSubjects);
            return "redirect:/admin/dashboard/groups/"+groupId+"/managers/"+userId+"/subjects";
        }

    }
    @GetMapping("admin/dashboard/groups/{groupId}/managers/{userId}/subjects/{subjectId}/remove")
    public String removeManagerSubject(@PathVariable String groupId, @PathVariable String userId,@PathVariable String subjectId) {
        User userById = userService.getUserById(userId);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        SubjectInfo subjectInfoById = subjectInfoService.getSubjectInfoById(subjectId);
        if (userById == null || groupInfoById == null|| subjectInfoById ==null) {
            return "redirect:/error";
        }
        ManagersGroupsSubjects managerGroupSubject = managersGroupsSubjectsService.getManagerGroupSubjectByGroupManagerSubject(groupInfoById,userById,subjectInfoById);
        if (managerGroupSubject == null) {
            return "redirect:/error";
        } else {
                managersGroupsSubjectsService.removeManagerGroupSubject(managerGroupSubject);
            return "redirect:/admin/dashboard/groups/"+groupId+"/managers/"+userId+"/subjects";
        }

    }

}
