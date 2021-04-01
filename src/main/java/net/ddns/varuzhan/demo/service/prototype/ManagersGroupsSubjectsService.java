package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.Set;

@Service
public interface ManagersGroupsSubjectsService {
    ManagersGroupsSubjects save(ManagersGroupsSubjects managersGroupsSubjects);
    Set<ManagersGroupsSubjects> getManagersGroupsAndSubjectsByGroupAndSubject(GroupInfo groupInfo, User user);
    Set<SubjectInfo> getSubjectsWithoutManagerAndGroupInfo(User user, GroupInfo groupInfo);
    ManagersGroupsSubjects getManagerGroupSubjectByGroupManagerSubject(GroupInfo groupInfo,User user,SubjectInfo subjectInfo);
    void removeManagerGroupSubject(ManagersGroupsSubjects managersGroupsSubjects);
    Set<ManagersGroupsSubjects> getManagersGroupAndSubjectInfos(User user);
    Set<ManagersGroupsSubjects> getManagerSubjectByGroup(GroupInfo groupInfo);
}
