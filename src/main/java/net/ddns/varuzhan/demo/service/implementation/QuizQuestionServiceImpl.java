package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.QuizQuestion;
import net.ddns.varuzhan.demo.repository.QuizQuestionRepository;
import net.ddns.varuzhan.demo.service.prototype.QuizQuestionService;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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

    @Override
    public QuizQuestion getQuizQuestionById(Integer id) {
        return quizQuestionRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteQuizQuestionByQuizQuestion(QuizQuestion quizQuestion) {
        quizQuestionRepository.delete(quizQuestion);
    }

    @Override
    public QuizQuestion getRandomQuestion(ClassMaterial classMaterial) {
        List<QuizQuestion> allQuestions = new LinkedList<>(quizQuestionRepository.findQuizQuestionsByClassMaterial(classMaterial));
        Collections.shuffle(allQuestions);
        if(allQuestions.size()>0)
            return allQuestions.get(0);
        else return null;

    }

    @Override
    public QuizQuestion getQuestionByToken(String token) {
        return quizQuestionRepository.findQuizQuestionByQuestionToken(token).orElse(null);
    }
}
