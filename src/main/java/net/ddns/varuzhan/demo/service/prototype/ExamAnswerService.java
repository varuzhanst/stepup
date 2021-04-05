package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.ExamAnswer;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public interface ExamAnswerService {
    ExamAnswer save(ExamAnswer examAnswer);
    ExamAnswer getExamAnswerByAttemptAndRealNumber(ExamAttempt examAttempt,Integer realNumber);
    Set<ExamAnswer> getExamAnswersByAttempt(ExamAttempt examAttempt);
}
