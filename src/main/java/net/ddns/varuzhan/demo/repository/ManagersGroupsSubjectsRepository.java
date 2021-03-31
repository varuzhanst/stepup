package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ManagersGroupsSubjectsRepository extends JpaRepository<ManagersGroupsSubjects, Integer> {
    Set<ManagersGroupsSubjects> findManagersGroupsSubjectsByGroupInfoAndUser(GroupInfo groupInfo, User user);
    Optional<ManagersGroupsSubjects> findManagersGroupsSubjectsByGroupInfoAndUserAndSubjectInfo(GroupInfo groupInfo,User user,SubjectInfo subjectInfo);
    Set<ManagersGroupsSubjects> findManagersGroupsSubjectsByUser(User user);
}
