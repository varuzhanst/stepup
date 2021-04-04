package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.Question;
import net.ddns.varuzhan.demo.repository.QuestionRepository;
import net.ddns.varuzhan.demo.service.prototype.QuestionService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestionsOfExam(Exam exam) {
        return questionRepository.findQuestionsByExam(exam);
    }

    @Override
    public void remove(Question question) {
        questionRepository.delete(question);
    }
}
