package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ManagersGroupsSubjectsService {
    ManagersGroupsSubjects save(ManagersGroupsSubjects managersGroupsSubjects);
    Set<ManagersGroupsSubjects> getAllManagersGroupsSubjects();
    Set<ManagersGroupsSubjects> getManagersGroupsUsersByManager(User user);
}
