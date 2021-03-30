package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.repository.UserGroupInfoRepository;
import net.ddns.varuzhan.demo.repository.UserRepository;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class UserGroupInfoServiceImpl implements UserGroupInfoService {
    private final UserGroupInfoRepository userGroupInfoRepository;
    private final UserService userService;

    public UserGroupInfoServiceImpl(UserGroupInfoRepository userGroupInfoRepository, UserService userService) {
        this.userGroupInfoRepository = userGroupInfoRepository;
        this.userService = userService;
    }

    @Override
    public UserGroupInfo save(UserGroupInfo userGroupInfo) {
        return userGroupInfoRepository.save(userGroupInfo);
    }

    @Override
    public UserGroupInfo getGroupInfoByUser(User user) {
        return userGroupInfoRepository.findUserGroupInfoByUser(user).orElse(null);
    }

    @Override
    public Set<User> getUsersByGroupInfo(GroupInfo groupInfo) {
        TreeSet<User> usersOfGroup = new TreeSet<>();
        List<UserGroupInfo> users = userGroupInfoRepository.findUserGroupInfosByGroupInfo(groupInfo);
        for (UserGroupInfo user : users) {
            if (user.getUser().getRole() == Role.USER)
                usersOfGroup.add(user.getUser());
        }
        return usersOfGroup;
    }

    @Override
    public Set<User> getManagersByGroupInfo(GroupInfo groupInfo) {
        TreeSet<User> managersOfGroup = new TreeSet<>();
        List<UserGroupInfo> users = userGroupInfoRepository.findUserGroupInfosByGroupInfo(groupInfo);
        for (UserGroupInfo user : users) {
            if (user.getUser().getRole() == Role.MANAGER)
                managersOfGroup.add(user.getUser());
        }
        return managersOfGroup;
    }

    @Override
    public Set<User> getUsersWithNoGroup() {
        Set<User> usersWithNoGroups = userService.getAllUsers();
        List<UserGroupInfo> usersWithGroup = userGroupInfoRepository.findAll();
        for(UserGroupInfo x : usersWithGroup){
            if(x.getUser().getRole()== Role.USER) usersWithNoGroups.remove(x.getUser());
        }
        return usersWithNoGroups;
    }

    @Override
    public UserGroupInfo getUserGroupInfoByGroupInfoAndUser(GroupInfo groupInfo, User user) {
        return userGroupInfoRepository.findUserGroupInfoByGroupInfoAndUser(groupInfo,user).get();
    }

    @Override
    public void removeUserGroupInfo(UserGroupInfo userGroupInfo) {
        userGroupInfoRepository.delete(userGroupInfo);
    }

    @Override
    public Set<User> getManagersOutOfGroup(GroupInfo groupInfo) {
        List<User> groupManagers = userGroupInfoRepository.findUserGroupInfosByGroupInfo(groupInfo)
                .stream()
                .filter(x->x.getUser().getRole()== Role.MANAGER)
                .map(y->y.getUser())
                .collect(Collectors.toList());
        Set<User> allManagers = userService.getAllManagers();
        HashSet<User> managersOutOfGroup = new HashSet<>();
        for(User x: allManagers){
            if(!groupManagers.contains(x)){
                managersOutOfGroup.add(x);
            }
        }

        return managersOutOfGroup;
    }
}
