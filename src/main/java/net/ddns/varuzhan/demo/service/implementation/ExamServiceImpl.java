package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.ExamRepository;
import net.ddns.varuzhan.demo.service.prototype.ExamService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Exam getExamById(String id) {
        try{
            return examRepository.findById(Integer.parseInt(id)).orElse(null);
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public void removeExam(Exam exam) {
            examRepository.delete(exam);

    }

    @Override
    public Set<Exam> examsOfManager(ManagersGroupsSubjects managersGroupsSubjects) {
        return examRepository.findExamsByManagersGroupsSubjects(managersGroupsSubjects);
    }

    @Override
    public Set<Exam> examsOfGroup(GroupInfo groupInfo) {
        HashSet<Exam> examsAll= new HashSet<>(examRepository.findAll());
        return examsAll.stream()
                .filter(exam -> exam.getPublished() && exam.getManagersGroupsSubjects().getGroupInfo().equals(groupInfo))
                .collect(Collectors.toSet());

    }
}
