package net.ddns.varuzhan.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity

public class AssignmentReturned implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Assignment assignment;
    @ManyToOne
    private User turnedInBy;
    @OneToOne
    private File file;
    private LocalDateTime turnedInAt;
    private LocalDateTime returnedAt;
    private Integer actualGrade;
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    public AssignmentReturned() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public User getTurnedInBy() {
        return turnedInBy;
    }

    public void setTurnedInBy(User turnedInBy) {
        this.turnedInBy = turnedInBy;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public LocalDateTime getTurnedInAt() {
        return turnedInAt;
    }

    public void setTurnedInAt(LocalDateTime turnedInAt) {
        this.turnedInAt = turnedInAt;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    public Integer getActualGrade() {
        return actualGrade;
    }

    public void setActualGrade(Integer actualGrade) {
        this.actualGrade = actualGrade;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    @Override
    public int compareTo(Object o) {
        return this.id-((AssignmentReturned)o).getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentReturned that = (AssignmentReturned) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
