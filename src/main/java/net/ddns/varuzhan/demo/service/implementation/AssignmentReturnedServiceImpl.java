package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Assignment;
import net.ddns.varuzhan.demo.model.AssignmentReturned;
import net.ddns.varuzhan.demo.model.AssignmentStatus;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.AssignmentReturnedRepository;
import net.ddns.varuzhan.demo.service.prototype.AssignmentReturnedService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
public class AssignmentReturnedServiceImpl implements AssignmentReturnedService {
    private final AssignmentReturnedRepository assignmentReturnedRepository;
    private final UserGroupInfoService userGroupInfoService;

    public AssignmentReturnedServiceImpl(AssignmentReturnedRepository assignmentReturnedRepository, UserGroupInfoService userGroupInfoService) {
        this.assignmentReturnedRepository = assignmentReturnedRepository;
        this.userGroupInfoService = userGroupInfoService;
    }

    @Override
    public AssignmentReturned save(AssignmentReturned assignmentReturned) {
        return assignmentReturnedRepository.save(assignmentReturned);
    }

    @Override
    public Set<AssignmentReturned> getReturnedAssignmentsByAssignment(Assignment assignment) {
        return assignmentReturnedRepository.findAssignmentReturnedByAssignment(assignment);
    }

    @Override
    public AssignmentReturned getAssignmentReturnedById(String id) {
        try{
            return assignmentReturnedRepository.findById(Integer.parseInt(id)).orElse(null);
        }
        catch (Exception e){
            return null;
        }

    }

    @Override
    public Set<AssignmentReturned> getAssignmentReturnedByUser(User user) {
        return assignmentReturnedRepository.findAssignmentReturnedByTurnedInBy(user);
    }

    @Override
    public Set<User> getAssignmentsNonTurnedIn(Assignment assignment) {
        HashSet<AssignmentReturned> allReturned = (HashSet<AssignmentReturned>) getReturnedAssignmentsByAssignment(assignment);
        Set<User> usersByGroupInfo = userGroupInfoService.getUsersByGroupInfo(assignment.getManagersGroupsSubjects().getGroupInfo());
        for (AssignmentReturned x : allReturned) {
            usersByGroupInfo.remove(x.getTurnedInBy());
        }
        return new TreeSet<>(usersByGroupInfo);
    }

    @Override
    public Set<AssignmentReturned> getAssignmentsTurnedIn(Assignment assignment) {
        HashSet<AssignmentReturned> allReturned = (HashSet<AssignmentReturned>) getReturnedAssignmentsByAssignment(assignment);
        return new TreeSet<>(allReturned
                .stream()
                .filter(x -> x.getStatus() == AssignmentStatus.TURNED_IN)
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<AssignmentReturned> getAssignmentsLateTurnedIn(Assignment assignment) {
        HashSet<AssignmentReturned> allReturned = (HashSet<AssignmentReturned>) getReturnedAssignmentsByAssignment(assignment);
        return new TreeSet<>(allReturned
                .stream()
                .filter(x -> x.getStatus() == AssignmentStatus.LATE_TURNED_IN)
                .collect(Collectors.toSet()));
    }

    @Override
    public Set<AssignmentReturned> getAssignmentsReturned(Assignment assignment) {
        HashSet<AssignmentReturned> allReturned = (HashSet<AssignmentReturned>) getReturnedAssignmentsByAssignment(assignment);
        return new TreeSet<>(allReturned
                .stream()
                .filter(x -> x.getStatus() == AssignmentStatus.RETURNED)
                .collect(Collectors.toSet()));
    }
}
