package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.QuizQuestion;
import net.ddns.varuzhan.demo.repository.QuizQuestionRepository;
import net.ddns.varuzhan.demo.service.prototype.QuizQuestionService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QuizQuestionServiceImpl implements QuizQuestionService {
    private final QuizQuestionRepository quizQuestionRepository;

    public QuizQuestionServiceImpl(QuizQuestionRepository quizQuestionRepository) {
        this.quizQuestionRepository = quizQuestionRepository;
    }

    @Override
    public void save(QuizQuestion quizQuestion) {
        quizQuestionRepository.save(quizQuestion);
    }

    @Override
    public Set<QuizQuestion> getQuestionsByClassMaterial(ClassMaterial classMaterial) {
        return quizQuestionRepository.findQuizQuestionsByClassMaterial(classMaterial);
    }
}
