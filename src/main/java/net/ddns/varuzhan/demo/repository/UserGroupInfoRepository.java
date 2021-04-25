package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.model.UserGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserGroupInfoRepository extends JpaRepository<UserGroupInfo,Integer> {
    Optional<UserGroupInfo> findUserGroupInfoByUser(User user);
    List<UserGroupInfo> findUserGroupInfosByGroupInfo(GroupInfo groupInfo);
    Set<UserGroupInfo> findUserGroupInfosByUser(User user);
    Optional<UserGroupInfo> findUserGroupInfoByGroupInfoAndUser(GroupInfo groupInfo,User user);
}
