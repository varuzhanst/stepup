package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.Assignment;


public class AssignmentsShowDto implements Comparable {
    private Assignment assignment;
    private Integer countOfNonTurnedInUsers;
    private Integer countOfTurnedInAssignments;
    private Integer countOfLateTurnedInUsers;
    private Integer countOfReturnedAssignments;

    public AssignmentsShowDto(Assignment assignment, Integer countOfNonTurnedInUsers, Integer countOfTurnedInAssignments, Integer countOfLateTurnedInUsers, Integer countOfReturnedAssignments) {
        this.assignment = assignment;
        this.countOfNonTurnedInUsers = countOfNonTurnedInUsers;
        this.countOfTurnedInAssignments = countOfTurnedInAssignments;
        this.countOfLateTurnedInUsers = countOfLateTurnedInUsers;
        this.countOfReturnedAssignments = countOfReturnedAssignments;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Integer getCountOfNonTurnedInUsers() {
        return countOfNonTurnedInUsers;
    }

    public void setCountOfNonTurnedInUsers(Integer countOfNonTurnedInUsers) {
        this.countOfNonTurnedInUsers = countOfNonTurnedInUsers;
    }

    public Integer getCountOfTurnedInAssignments() {
        return countOfTurnedInAssignments;
    }

    public void setCountOfTurnedInAssignments(Integer countOfTurnedInAssignments) {
        this.countOfTurnedInAssignments = countOfTurnedInAssignments;
    }

    public Integer getCountOfLateTurnedInUsers() {
        return countOfLateTurnedInUsers;
    }

    public void setCountOfLateTurnedInUsers(Integer countOfLateTurnedInUsers) {
        this.countOfLateTurnedInUsers = countOfLateTurnedInUsers;
    }

    public Integer getCountOfReturnedAssignments() {
        return countOfReturnedAssignments;
    }

    public void setCountOfReturnedAssignments(Integer countOfReturnedAssignments) {
        this.countOfReturnedAssignments = countOfReturnedAssignments;
    }

    @Override
    public int compareTo(Object o) {
        return this.assignment.compareTo(((AssignmentsShowDto)o).assignment);
    }

}
