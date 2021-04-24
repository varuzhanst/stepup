package net.ddns.varuzhan.demo.dto;

public class QuizAnswerDto {
    private String questionToken;

    public String getQuestionToken() {
        return questionToken;
    }

    public void setQuestionToken(String questionToken) {
        this.questionToken = questionToken;
    }

    private String selectedOption;

    public QuizAnswerDto() {

    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
