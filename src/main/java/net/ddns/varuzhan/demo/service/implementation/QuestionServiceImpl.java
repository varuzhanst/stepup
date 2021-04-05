package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.Question;
import net.ddns.varuzhan.demo.repository.QuestionRepository;
import net.ddns.varuzhan.demo.service.prototype.QuestionService;
import org.springframework.stereotype.Component;

import java.util.*;

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

    @Override
    public HashSet<Question> getQuestionsForExamAttempt(Exam exam) {
        Random random = new Random();
        List<Question> allQuestions = new ArrayList<>(questionRepository.findQuestionsByExam(exam));
        HashSet<Question> examQuestions = new HashSet<>();
        for (int i = 0; i < exam.getCountOfQuestions(); i++) {
            int randomId = random.nextInt(allQuestions.size());
            examQuestions.add(allQuestions.get(randomId));
            allQuestions.remove(randomId);
        }
        return examQuestions;
    }
}
