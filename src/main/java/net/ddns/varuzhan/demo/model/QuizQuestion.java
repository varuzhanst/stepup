package net.ddns.varuzhan.demo.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class QuizQuestion implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ClassMaterial classMaterial;
    private String questionText;
    private String questionToken;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    private String option1text;
    private String option2text;
    private String option3text;
    private String option4text;
    private Integer correctOption;
    private String notes;

    public QuizQuestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClassMaterial getClassMaterial() {
        return classMaterial;
    }

    public void setClassMaterial(ClassMaterial classMaterial) {
        this.classMaterial = classMaterial;
    }

    public String getOption1text() {
        return option1text;
    }

    public void setOption1text(String option1text) {
        this.option1text = option1text;
    }

    public String getOption2text() {
        return option2text;
    }

    public void setOption2text(String option2text) {
        this.option2text = option2text;
    }

    public String getOption3text() {
        return option3text;
    }

    public void setOption3text(String option3text) {
        this.option3text = option3text;
    }

    public String getOption4text() {
        return option4text;
    }

    public void setOption4text(String option4text) {
        this.option4text = option4text;
    }

    public Integer getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(Integer correctOption) {
        this.correctOption = correctOption;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getQuestionToken() {
        return questionToken;
    }

    public void setQuestionToken(String questionToken) {
        this.questionToken = questionToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestion that = (QuizQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Boolean isOption3Visible(){
        return option3text!=null;
    }
    public Boolean isOption4Visible(){
        return option4text!=null;
    }

    @Override
    public int compareTo(Object o) {
        return this.id-((QuizQuestion)o).getId();
    }
}
