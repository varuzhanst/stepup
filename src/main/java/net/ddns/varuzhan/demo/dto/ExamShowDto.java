package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.ExamStatus;

public class ExamShowDto implements Comparable{
    private Exam exam;
    private Integer addedQuestionsCount;

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        this.status = status;
    }

    private ExamStatus status;

    public ExamShowDto(Exam exam, Integer addedQuestionsCount, ExamStatus status) {
        this.exam = exam;
        this.addedQuestionsCount = addedQuestionsCount;
        this.status = status;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Integer getAddedQuestionsCount() {
        return addedQuestionsCount;
    }

    public void setAddedQuestionsCount(Integer addedQuestionsCount) {
        this.addedQuestionsCount = addedQuestionsCount;
    }

    @Override
    public int compareTo(Object o) {
        return this.exam.compareTo(((ExamShowDto)o).getExam());
    }
}
