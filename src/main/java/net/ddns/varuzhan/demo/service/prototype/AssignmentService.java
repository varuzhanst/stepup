package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.Assignment;
import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AssignmentService {
    void save(Assignment assignment);
    Set<Assignment> getAssignmentsByManagerGroupSubject(ManagersGroupsSubjects managerGroupSubject);
    Assignment getAssignmentById(String id);
    void removeAssignment(Assignment assignmentById);
}
