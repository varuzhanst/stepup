package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface UserGroupInfoService {
    UserGroupInfo save(UserGroupInfo userGroupInfo);

    UserGroupInfo getGroupInfoByUser(User user);

    Set<User> getUsersByGroupInfo(GroupInfo groupInfo);

    Set<User> getManagersByGroupInfo(GroupInfo groupInfo);

    Set<UserGroupInfo> getGroupInfosByUser(User user);

    Set<User> getUsersWithNoGroup();

    UserGroupInfo getUserGroupInfoByGroupInfoAndUser(GroupInfo groupInfo, User user);

    void removeUserGroupInfo(UserGroupInfo userGroupInfo);

    Set<User> getManagersOutOfGroup(GroupInfo groupInfo);
}
