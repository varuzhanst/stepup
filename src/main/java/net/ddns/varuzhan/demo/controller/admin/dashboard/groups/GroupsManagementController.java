package net.ddns.varuzhan.demo.controller.admin.dashboard.groups;

import net.ddns.varuzhan.demo.dto.NewGroupDto;
import net.ddns.varuzhan.demo.dto.NewGroupUserDto;
import net.ddns.varuzhan.demo.dto.SubjectsManagementDto;
import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.TreeSet;

@RequestMapping
@Controller
public class GroupsManagementController {
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final UserGroupInfoService userGroupInfoService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;

    public GroupsManagementController(UserService userService, GroupInfoService groupInfoService, UserGroupInfoService userGroupInfoService, ManagersGroupsSubjectsService managersGroupsSubjectsService) {
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.userGroupInfoService = userGroupInfoService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
    }

    @PostMapping("admin/dashboard/groups/new")
    public String newGroup(@ModelAttribute("newGroup") NewGroupDto newGroupDto) {
        if (groupInfoService.getGroupInfoByGroupNumber(newGroupDto.getGroupNumber()).size() != 0) {
            return "redirect:/admin/dashboard?groupAlreadyExists";
        } else {
            GroupInfo groupInfo = new GroupInfo(newGroupDto.getGroupNumber());
            groupInfoService.save(groupInfo);
            return "redirect:/admin/dashboard?groupAdditionSuccess";
        }
    }

    @GetMapping("admin/dashboard/groups/{groupId}/users")
    public String loadUsersById(@PathVariable String groupId, Model model) {
        GroupInfo group = groupInfoService.getGroupInfoById(groupId);
        if (group == null) {
            return "redirect:/admin/dashboard";
        } else {
            TreeSet<User> users = (TreeSet<User>) userGroupInfoService.getUsersByGroupInfo(group);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                    + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                    + " " + userService.getUserByEmail(authentication.getName()).getLastName();
            model.addAttribute("full_name", fullName);
            model.addAttribute("groupNumber", group.getGroupNumber());
            model.addAttribute("groupUsers", users);
            model.addAttribute("newGroupUser", new NewGroupUserDto());
            model.addAttribute("usersWithNoGroup", userGroupInfoService.getUsersWithNoGroup());
            return "admin/dashboard/group/groupUsers";
        }
    }

    @PostMapping("admin/dashboard/groups/{groupId}/users")
    public String newGroupUser(@PathVariable(required = false) String groupId, @ModelAttribute("newUser") NewGroupUserDto newGroupUserDto) {
        UserGroupInfo userGroupInfo = new UserGroupInfo();
        GroupInfo group = groupInfoService.getGroupInfoById(groupId);
        User user = userService.getUserById(newGroupUserDto.getUserId());
        if (group == null || user == null) {
            return "redirect:/error";
        } else {
            userGroupInfo.setGroupInfo(group);
            userGroupInfo.setUser(user);
            userGroupInfoService.save(userGroupInfo);
            return "redirect:/admin/dashboard/groups/" + groupId + "/users";
        }
    }

    @PostMapping("admin/dashboard/groups/{groupId}/managers")
    public String newGroupManager(@PathVariable(required = false) String groupId, @ModelAttribute("newUser") NewGroupUserDto newGroupUserDto) {
        UserGroupInfo userGroupInfo = new UserGroupInfo();
        GroupInfo group = groupInfoService.getGroupInfoById(groupId);
        User user = userService.getUserById(newGroupUserDto.getUserId());
        if (group == null || user == null) {
            return "redirect:/error";
        } else {
            userGroupInfo.setGroupInfo(group);
            userGroupInfo.setUser(user);
            userGroupInfoService.save(userGroupInfo);
            return "redirect:/admin/dashboard/groups/" + groupId + "/managers";
        }
    }

    @GetMapping("admin/dashboard/groups/{groupId}/managers")
    public String loadManagersById(@PathVariable String groupId, Model model) {
        GroupInfo group = groupInfoService.getGroupInfoById(groupId);
        if (group == null) {
            return "redirect:/admin/dashboard";
        } else {
            TreeSet<User> users = (TreeSet<User>) userGroupInfoService.getManagersByGroupInfo(group);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                    + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                    + " " + userService.getUserByEmail(authentication.getName()).getLastName();
            model.addAttribute("full_name", fullName);
            model.addAttribute("groupNumber", group.getGroupNumber());
            TreeSet<SubjectsManagementDto> subjectsManagementDto = new TreeSet<>();
            for (User x : users) {
                subjectsManagementDto.add(new SubjectsManagementDto(x, managersGroupsSubjectsService.getManagersGroupsAndSubjectsByGroupAndSubject(group, x).size()));
            }
            model.addAttribute("groupUsers", subjectsManagementDto);
            model.addAttribute("newGroupUser", new NewGroupUserDto());
            model.addAttribute("managersOutOfGroup", userGroupInfoService.getManagersOutOfGroup(group));

            return "admin/dashboard/group/groupManagers";
        }
    }

    @GetMapping("admin/dashboard/groups/{groupId}/users/{userId}/remove")
    public String removeUserFromGroup(@PathVariable String groupId, @PathVariable String userId) {
        User userById = userService.getUserById(userId);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        if (userById == null || groupInfoById == null) {
            return "redirect:/error";
        }
        UserGroupInfo userGroupInfo = userGroupInfoService.getUserGroupInfoByGroupInfoAndUser(groupInfoById, userById);
        if (userGroupInfo == null) {
            return "redirect:/error";
        } else {
            userGroupInfoService.removeUserGroupInfo(userGroupInfo);
            return "redirect:/admin/dashboard/groups/" + groupId + "/users";
        }

    }

    @GetMapping("admin/dashboard/groups/{groupId}/managers/{userId}/remove")
    public String removeManagerFromGroup(@PathVariable String groupId, @PathVariable String userId) {
        User userById = userService.getUserById(userId);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        if (userById == null || groupInfoById == null) {
            return "redirect:/error";
        }
        UserGroupInfo userGroupInfo = userGroupInfoService.getUserGroupInfoByGroupInfoAndUser(groupInfoById, userById);
        if (userGroupInfo == null) {
            return "redirect:/error";
        } else {
            userGroupInfoService.removeUserGroupInfo(userGroupInfo);
            return "redirect:/admin/dashboard/groups/" + groupId + "/managers";
        }

    }

}
