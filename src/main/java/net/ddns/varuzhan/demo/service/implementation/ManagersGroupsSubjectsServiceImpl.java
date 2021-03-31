package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.ManagersGroupsSubjectsRepository;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import net.ddns.varuzhan.demo.service.prototype.SubjectInfoService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Component
public class ManagersGroupsSubjectsServiceImpl implements ManagersGroupsSubjectsService {
    private final ManagersGroupsSubjectsRepository managersGroupsSubjectsRepository;
    private final SubjectInfoService subjectInfoService;
    public ManagersGroupsSubjectsServiceImpl(ManagersGroupsSubjectsRepository managersGroupsSubjectsRepository, SubjectInfoService subjectInfoService) {
        this.managersGroupsSubjectsRepository = managersGroupsSubjectsRepository;
        this.subjectInfoService = subjectInfoService;
    }

    @Override
    public ManagersGroupsSubjects save(ManagersGroupsSubjects managersGroupsSubjects) {
        return managersGroupsSubjectsRepository.save(managersGroupsSubjects);
    }

    @Override
    public Set<ManagersGroupsSubjects> getManagersGroupsAndSubjectsByGroupAndSubject(GroupInfo groupInfo, User user) {
        return managersGroupsSubjectsRepository.findManagersGroupsSubjectsByGroupInfoAndUser(groupInfo,user);
    }

    @Override
    public Set<SubjectInfo> getSubjectsWithoutManagerAndGroupInfo(User user, GroupInfo groupInfo) {
        TreeSet<SubjectInfo> eligibleSubjects = (TreeSet<SubjectInfo>) subjectInfoService.getAllSubjectInfos();
        HashSet<ManagersGroupsSubjects> subjectsOfManager = (HashSet<ManagersGroupsSubjects>)managersGroupsSubjectsRepository.findManagersGroupsSubjectsByGroupInfoAndUser(groupInfo, user);
        for(ManagersGroupsSubjects x: subjectsOfManager){
            eligibleSubjects.remove(x.getSubjectInfo());
        }
        return eligibleSubjects;
    }

    @Override
    public ManagersGroupsSubjects getManagerGroupSubjectByGroupManagerSubject(GroupInfo groupInfo, User user, SubjectInfo subjectInfo) {
        return managersGroupsSubjectsRepository.findManagersGroupsSubjectsByGroupInfoAndUserAndSubjectInfo(groupInfo,user,subjectInfo).orElse(null);
    }

    @Override
    public void removeManagerGroupSubject(ManagersGroupsSubjects managersGroupsSubjects) {
        managersGroupsSubjectsRepository.delete(managersGroupsSubjects);
    }

    @Override
    public Set<ManagersGroupsSubjects> getManagersGroupAndSubjectInfos(User user) {
        return managersGroupsSubjectsRepository.findManagersGroupsSubjectsByUser(user);
    }


}
