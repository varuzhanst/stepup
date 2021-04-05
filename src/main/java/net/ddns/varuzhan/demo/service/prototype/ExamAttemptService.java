package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ExamAttemptService {
    ExamAttempt save(ExamAttempt examAttempt);
    ExamAttempt getAttemptByUserAndExam(User user, Exam exam);
    double calculateExamGrade(ExamAttempt examAttempt);
}
