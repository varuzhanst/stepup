package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.Assignment;
import net.ddns.varuzhan.demo.model.AssignmentReturned;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface AssignmentReturnedService {
    AssignmentReturned save(AssignmentReturned assignmentReturned);
    Set<AssignmentReturned> getReturnedAssignmentsByAssignment(Assignment assignment);
    AssignmentReturned getAssignmentReturnedById(String id);
    Set<AssignmentReturned> getAssignmentReturnedByUser(User user);
    Set<User> getAssignmentsNonTurnedIn(Assignment assignment);
    Set<AssignmentReturned> getAssignmentsTurnedIn(Assignment assignment);
    Set<AssignmentReturned> getAssignmentsLateTurnedIn(Assignment assignment);
    Set<AssignmentReturned> getAssignmentsReturned(Assignment assignment);
}
