package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.Exam;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ExamRepository extends JpaRepository<Exam,Integer> {
    Set<Exam> findExamsByManagersGroupsSubjects(ManagersGroupsSubjects managersGroupsSubjects);
}
