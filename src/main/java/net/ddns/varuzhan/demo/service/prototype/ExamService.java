package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ExamService {
    Exam save(Exam exam);
    Exam getExamById(String id);
    void removeExam(Exam exam);
    Set<Exam> examsOfManager(ManagersGroupsSubjects managersGroupsSubjects);
    Set<Exam> examsOfGroup(GroupInfo groupInfo);
}
