package net.ddns.varuzhan.demo.controller.admin.dashboard;


import net.ddns.varuzhan.demo.dto.GroupsManagementDto;
import net.ddns.varuzhan.demo.dto.NewGroupDto;
import net.ddns.varuzhan.demo.dto.NewSubjectDto;
import net.ddns.varuzhan.demo.dto.UsersShowDto;
import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.SubjectInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboard {

    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final UserGroupInfoService userGroupInfoService;
    private final SubjectInfoService subjectInfoService;
    public AdminDashboard(UserService userService, GroupInfoService groupInfoService, UserGroupInfoService userGroupInfoService, SubjectInfoService subjectInfoService) {
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.userGroupInfoService = userGroupInfoService;
        this.subjectInfoService = subjectInfoService;
    }

    @GetMapping
    public String loadAdminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        model.addAttribute("full_name", fullName);
        Set<User> users = userService.getAllUsers();
        Set<User> managers = userService.getAllManagers();
        TreeSet<UsersShowDto> usersShowDtos = new TreeSet<>();
        TreeSet<UsersShowDto> managersShowDtos = new TreeSet<>();
        for(User x:users){
            String group="-";
            if(userGroupInfoService.getGroupInfoByUser(x)!=null)
                group = userGroupInfoService.getGroupInfoByUser(x).getGroupInfo().getGroupNumber();
            usersShowDtos.add(new UsersShowDto(x,group));
        }
        StringBuilder groups= new StringBuilder("-");
        for(User x:managers){
            if(!userGroupInfoService.getGroupInfosByUser(x).isEmpty()){
                TreeSet<UserGroupInfo> userGroupInfos = new TreeSet<>(userGroupInfoService.getGroupInfosByUser(x));
                groups = new StringBuilder();
                for(UserGroupInfo y:userGroupInfos){
                    groups.append(y.getGroupInfo().getGroupNumber()).append("\n");
                }
            }
            managersShowDtos.add(new UsersShowDto(x, groups.toString()));

        }
        model.addAttribute("managers",managersShowDtos);
        model.addAttribute("users",usersShowDtos);
        TreeSet<GroupsManagementDto> groupsManagementDtos = new TreeSet<>();
        HashSet<GroupInfo> allGroups = (HashSet<GroupInfo>) groupInfoService.getAllGroups();
        for(GroupInfo x: allGroups){
            groupsManagementDtos.add(new GroupsManagementDto(x,String.valueOf(userGroupInfoService.getUsersByGroupInfo(x).size()),String.valueOf(userGroupInfoService.getManagersByGroupInfo(x).size())));
        }
        model.addAttribute("groups",groupsManagementDtos);
        model.addAttribute("newGroup",new NewGroupDto());
        model.addAttribute("subjects",subjectInfoService.getAllSubjectInfos());
        model.addAttribute("newSubject",new NewSubjectDto());
        return "admin/dashboard/adminDashboard";
    }
    @GetMapping({"groups/{groupId}/users/{userId}/changeAccountStatus",
            "groups/{groupId}/managers/{userId}/changeAccountStatus"})
    public String changeAccountStatusFromGroup(@PathVariable(required = false) String groupId, @PathVariable String userId){
        User userById = userService.getUserById(userId);
        if(userById==null){
            return "redirect:/error";
        }
        else{
            userById.setLocked(!userById.getLocked());
            userService.save(userById);
            if(userById.getRole()== Role.USER)
            return "redirect:/admin/dashboard/groups/"+groupId+"/users";
            else  return "redirect:/admin/dashboard/groups/"+groupId+"/managers";
        }

    }
    @GetMapping("users/{userId}/changeAccountStatus")
    public String changeAccountStatusFromDashboard(@PathVariable String userId){
        User userById = userService.getUserById(userId);
        if(userById==null){
            return "redirect:/error";
        }
        else{
            userById.setLocked(!userById.getLocked());
            userService.save(userById);
            return "redirect:/admin/dashboard";
        }

    }
    @GetMapping("users/{userId}/resetAccount")
    public String resetAccount(@PathVariable String userId){
        User userById = userService.getUserById(userId);
        if(userById==null){
            return "redirect:/error";
        }
        else{
            userById.setLocked(false);
            userById.setEnabled(false);
            userById.setPassword(null);
            userService.save(userById);
            return "redirect:/admin/dashboard";
        }

    }
}
