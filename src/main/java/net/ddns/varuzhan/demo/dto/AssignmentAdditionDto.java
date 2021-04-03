package net.ddns.varuzhan.demo.dto;


import java.time.LocalDateTime;

public class AssignmentAdditionDto {
    private String assignmentName;
    private String deadline;
    private String maxGrade;

    public AssignmentAdditionDto() {
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDeadline() {
        return deadline;
    }

    public LocalDateTime getDeadlineLocal() {
        return LocalDateTime.parse(deadline);
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }
}
