package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Assignment;
import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.repository.AssignmentRepository;
import net.ddns.varuzhan.demo.service.prototype.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AssignmentServiceImpl implements AssignmentService {
    final private AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public Assignment save(Assignment assignment) {
       return assignmentRepository.save(assignment);
    }

    @Override
    public Set<Assignment> getAssignmentsByManagerGroupSubject(ManagersGroupsSubjects managerGroupSubject) {
        return assignmentRepository.findAssignmentsByManagersGroupsSubjects(managerGroupSubject);
    }

    @Override
    public Assignment getAssignmentById(String id) {
        return assignmentRepository.findById(Integer.parseInt(id)).orElse(null);
    }

    @Override
    public void removeAssignment(Assignment assignmentById) {
        assignmentRepository.delete(assignmentById);
    }
}
