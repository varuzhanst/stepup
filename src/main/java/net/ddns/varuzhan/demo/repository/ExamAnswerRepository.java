package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ExamAnswer;
import net.ddns.varuzhan.demo.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Integer> {
    Optional<ExamAnswer> findExamAnswerByExamAttemptAndRealNumber(ExamAttempt examAttempt, Integer realNumber);
    Set<ExamAnswer> findExamAnswersByExamAttempt(ExamAttempt examAttempt);
}
