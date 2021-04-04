package net.ddns.varuzhan.demo.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Assignment implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String assignmentName;
    private LocalDateTime addedAt;
    private LocalDateTime deadline;
    @ManyToOne
    private File descriptionFile;
    @ManyToOne
    private ManagersGroupsSubjects managersGroupsSubjects;
    private Integer maxGrade;

    public Assignment() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }


    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public File getDescriptionFile() {
        return descriptionFile;
    }

    public void setDescriptionFile(File descriptionFile) {
        this.descriptionFile = descriptionFile;
    }


    public ManagersGroupsSubjects getManagersGroupsSubjects() {
        return managersGroupsSubjects;
    }

    public void setManagersGroupsSubjects(ManagersGroupsSubjects managersGroupsSubjects) {
        this.managersGroupsSubjects = managersGroupsSubjects;
    }


    public Integer getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Integer maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getAddedAtNormal(){
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.addedAt);
    }
    public String getDeadlineNormal(){
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.deadline);
    }

    @Override
    public int compareTo(Object o) {
        return ((Assignment)o).getDeadline().compareTo(this.getDeadline());
    }
}
