package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.ExamAnswer;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import net.ddns.varuzhan.demo.repository.ExamAnswerRepository;
import net.ddns.varuzhan.demo.service.prototype.ExamAnswerService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ExamAnswerServiceImpl implements ExamAnswerService {
    private final ExamAnswerRepository examAnswerRepository;

    public ExamAnswerServiceImpl(ExamAnswerRepository examAnswerRepository) {
        this.examAnswerRepository = examAnswerRepository;
    }

    @Override
    public ExamAnswer save(ExamAnswer examAnswer) {
        return examAnswerRepository.save(examAnswer);
    }

    @Override
    public ExamAnswer getExamAnswerByAttemptAndRealNumber(ExamAttempt examAttempt, Integer realNumber) {
        return examAnswerRepository.findExamAnswerByExamAttemptAndRealNumber(examAttempt,realNumber).orElse(null);
    }

    @Override
    public Set<ExamAnswer> getExamAnswersByAttempt(ExamAttempt examAttempt) {
        return examAnswerRepository.findExamAnswersByExamAttempt(examAttempt);
    }

}
