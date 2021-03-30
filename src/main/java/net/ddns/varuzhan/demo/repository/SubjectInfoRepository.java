package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface SubjectInfoRepository extends JpaRepository<SubjectInfo,Integer> {
    Optional<SubjectInfo> findSubjectInfoBySubjectName(String subjectName);
}
