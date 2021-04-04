package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Set<Question> findQuestionsByExam(Exam exam);
}
