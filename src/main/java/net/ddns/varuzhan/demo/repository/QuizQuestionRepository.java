package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion,Integer> {
    Set<QuizQuestion> findQuizQuestionsByClassMaterial(ClassMaterial classMaterial);
    Optional<QuizQuestion> findQuizQuestionByQuestionToken(String questionToken);
}
