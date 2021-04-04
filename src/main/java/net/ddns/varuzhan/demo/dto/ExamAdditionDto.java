package net.ddns.varuzhan.demo.dto;

public class ExamAdditionDto {
    private String examName;
    private String startAt;
    private String maxGrade;
    private String duration;
    private String questionsCount;
    private String choicesCount;

    public ExamAdditionDto() {
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getMaxGrade() {
        return maxGrade;
    }

    public void setMaxGrade(String maxGrade) {
        this.maxGrade = maxGrade;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(String questionsCount) {
        this.questionsCount = questionsCount;
    }

    public String getChoicesCount() {
        return choicesCount;
    }

    public void setChoicesCount(String choicesCount) {
        this.choicesCount = choicesCount;
    }
}
