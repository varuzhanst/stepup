package net.ddns.varuzhan.demo.controller.admin.dashboard;


import net.ddns.varuzhan.demo.dto.GroupsManagementDto;
import net.ddns.varuzhan.demo.dto.NewGroupDto;
import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
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
import java.util.TreeSet;


@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboard {

    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final UserGroupInfoService userGroupInfoService;
    public AdminDashboard(UserService userService, GroupInfoService groupInfoService, UserGroupInfoService userGroupInfoService) {
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.userGroupInfoService = userGroupInfoService;
    }

    @GetMapping
    public String loadAdminDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        model.addAttribute("full_name", fullName);
        model.addAttribute("managers",userService.getAllManagers());
        TreeSet<GroupsManagementDto> groupsManagementDtos = new TreeSet<>();
        HashSet<GroupInfo> allGroups = (HashSet<GroupInfo>) groupInfoService.getAllGroups();
        for(GroupInfo x: allGroups){
            groupsManagementDtos.add(new GroupsManagementDto(x,String.valueOf(userGroupInfoService.getUsersByGroupInfo(x).size()),String.valueOf(userGroupInfoService.getManagersByGroupInfo(x).size())));
        }
        model.addAttribute("groups",groupsManagementDtos);
        model.addAttribute("newGroup",new NewGroupDto());
        return "admin/dashboard/adminDashboard";
    }
    @GetMapping({"groups/{groupId}/users/{userId}/changeAccountStatus",
            "groups/{groupId}/managers/{userId}/changeAccountStatus"})
    public String changeAccountStatus(@PathVariable(required = false) String groupId, @PathVariable String userId){
        User userById = userService.getUserById(userId);
        if(userById==null){
            return "redirect:/error";
        }
        else{
            userById.setLocked(!userById.getLocked());
            userService.save(userById);
            return "redirect:/admin/dashboard/groups/"+groupId+"/users";
        }

    }
}
