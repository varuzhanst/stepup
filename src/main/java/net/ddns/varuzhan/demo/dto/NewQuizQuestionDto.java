package net.ddns.varuzhan.demo.dto;

public class NewQuizQuestionDto {
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String notes;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public NewQuizQuestionDto() {
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

}
