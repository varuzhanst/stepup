package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserGroupInfoService {
    UserGroupInfo save(UserGroupInfo userGroupInfo);
    UserGroupInfo getGroupInfoByUser(User user);
}
