package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.Question;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface QuestionService {
    Question save(Question question);
    Set<Question> getQuestionsOfExam(Exam exam);
    void remove(Question question);

}
