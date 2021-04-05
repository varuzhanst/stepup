package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.ExamAnswer;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Integer> {
    Optional<ExamAttempt> findExamAttemptByUserAndExam(User user, Exam exam);
}
