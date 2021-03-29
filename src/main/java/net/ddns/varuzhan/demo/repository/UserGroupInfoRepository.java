package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserGroupInfoRepository extends JpaRepository<UserGroupInfo,Integer> {
    Optional<UserGroupInfo> findUserGroupInfoByUser(User user);
    List<UserGroupInfo> findUserGroupInfosByGroupInfo(GroupInfo groupInfo);
    Optional<UserGroupInfo> findUserGroupInfoByGroupInfoAndUser(GroupInfo groupInfo,User user);
}
