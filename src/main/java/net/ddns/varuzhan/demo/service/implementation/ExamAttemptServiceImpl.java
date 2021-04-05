package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.ExamAnswer;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.ExamAttemptRepository;
import net.ddns.varuzhan.demo.service.prototype.ExamAnswerService;
import net.ddns.varuzhan.demo.service.prototype.ExamAttemptService;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ExamAttemptServiceImpl implements ExamAttemptService {
    private final ExamAttemptRepository examAttemptRepository;
    private final ExamAnswerService examAnswerService;

    public ExamAttemptServiceImpl(ExamAttemptRepository examAttemptRepository, ExamAnswerService examAnswerService) {
        this.examAttemptRepository = examAttemptRepository;
        this.examAnswerService = examAnswerService;
    }

    @Override
    public ExamAttempt save(ExamAttempt examAttempt) {
        return examAttemptRepository.save(examAttempt);
    }

    @Override
    public ExamAttempt getAttemptByUserAndExam(User user, Exam exam) {
        return examAttemptRepository.findExamAttemptByUserAndExam(user,exam).orElse(null);
    }

    @Override
    public double calculateExamGrade(ExamAttempt examAttempt) {
        int countOfCorrect=0;
        HashSet<ExamAnswer> examAnswersByAttempt = new HashSet<>(examAnswerService.getExamAnswersByAttempt(examAttempt));
        for(ExamAnswer x : examAnswersByAttempt){
            if(x.getSelectedAnswer()!=null){
                if(x.getSelectedAnswer().equals(x.getQuestion().getCorrectOption())){
                    countOfCorrect++;
                }
            }
        }
        return ((double) countOfCorrect /(double)examAttempt.getExam ().getCountOfQuestions())* (double)examAttempt.getExam().getMaxGrade();
    }


}
