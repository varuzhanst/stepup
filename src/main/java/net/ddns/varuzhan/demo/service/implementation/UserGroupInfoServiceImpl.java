package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import net.ddns.varuzhan.demo.repository.UserGroupInfoRepository;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import org.springframework.stereotype.Component;

@Component
public class UserGroupInfoServiceImpl implements UserGroupInfoService {
    private final UserGroupInfoRepository userGroupInfoRepository;

    public UserGroupInfoServiceImpl(UserGroupInfoRepository userGroupInfoRepository) {
        this.userGroupInfoRepository = userGroupInfoRepository;
    }

    @Override
    public UserGroupInfo save(UserGroupInfo userGroupInfo) {
        return userGroupInfoRepository.save(userGroupInfo);
    }

    @Override
    public UserGroupInfo getGroupInfoByUser(User user) {
        return userGroupInfoRepository.findUserGroupInfoByUser(user).orElse(null);
    }
}
