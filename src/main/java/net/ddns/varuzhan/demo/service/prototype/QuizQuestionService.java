package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.QuizQuestion;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QuizQuestionService {
    void save(QuizQuestion quizQuestion);
    Set<QuizQuestion> getQuestionsByClassMaterial(ClassMaterial classMaterial);
}
