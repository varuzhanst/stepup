package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserDashboard {
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;

    public UserDashboard(UserService userService, UserGroupInfoService userGroupInfoService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
    }

    @RequestMapping("/user/dashboard")
    public String loadUserDashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        UserGroupInfo groupInfoByUser = userGroupInfoService.getGroupInfoByUser(userService.getUserByEmail(authentication.getName()));
        if(groupInfoByUser==null) return "redirect:/login?noGroup";
        String groupNumber = " (" + groupInfoByUser.getGroupInfo().getGroupNumber() + ")";
        model.addAttribute("full_name_group_number", fullName + groupNumber );
        return "user/dashboard/userDashboard";
    }
}
