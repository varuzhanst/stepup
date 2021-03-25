package net.ddns.varuzhan.demo.bootstrap;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.service.prototype.GroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


public class BootStrapData implements CommandLineRunner {
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final UserGroupInfoService userGroupInfoService;

    public BootStrapData(UserService userService, GroupInfoService groupInfoService, UserGroupInfoService userGroupInfoService) {
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.userGroupInfoService = userGroupInfoService;
    }

    @Override
    public void run(String... args) throws Exception {

        User user = userService.getUserByEmail("admin");
        GroupInfo groupInfo = new GroupInfo("Հ755-2");
        groupInfo=groupInfoService.save(groupInfo);
        UserGroupInfo userGroupInfo = new UserGroupInfo(user,groupInfo);
        userGroupInfoService.save(userGroupInfo);

    }
}
