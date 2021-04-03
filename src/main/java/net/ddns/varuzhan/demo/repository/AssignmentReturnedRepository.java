package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.Assignment;
import net.ddns.varuzhan.demo.model.AssignmentReturned;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface AssignmentReturnedRepository extends JpaRepository<AssignmentReturned,Integer> {
    Set<AssignmentReturned> findAssignmentReturnedByAssignment(Assignment assignment);
    Set<AssignmentReturned> findAssignmentReturnedByTurnedInBy(User turnedInBy);
}
