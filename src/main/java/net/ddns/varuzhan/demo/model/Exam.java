package net.ddns.varuzhan.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Exam implements Comparable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime addedAt;
    private LocalDateTime startAt;
    private Integer duration;
    private Integer maxGrade;
    private Boolean published;
    @ManyToOne
    private ManagersGroupsSubjects managersGroupsSubjects;
    private String examName;
    private Integer countOfQuestions;

    public Exam() {
    }

    public Integer getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(Integer maxGrade) {
        this.maxGrade = maxGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(id, exam.id);
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

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public ManagersGroupsSubjects getManagersGroupsSubjects() {
        return managersGroupsSubjects;
    }

    public void setManagersGroupsSubjects(ManagersGroupsSubjects managersGroupsSubjects) {
        this.managersGroupsSubjects = managersGroupsSubjects;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getCountOfQuestions() {
        return countOfQuestions;
    }

    public void setCountOfQuestions(Integer countOfQuestions) {
        this.countOfQuestions = countOfQuestions;
    }


    @Override
    public int compareTo(Object o) {
        return this.id - ((Exam) o).getId();
    }

    public Exam(LocalDateTime addedAt, LocalDateTime startAt, Integer duration, Integer maxGrade, Boolean published, ManagersGroupsSubjects managersGroupsSubjects, String examName, Integer countOfQuestions) {
        this.addedAt = addedAt;
        this.startAt = startAt;
        this.duration = duration;
        this.maxGrade = maxGrade;
        this.published = published;
        this.managersGroupsSubjects = managersGroupsSubjects;
        this.examName = examName;
        this.countOfQuestions = countOfQuestions;
    }
}
