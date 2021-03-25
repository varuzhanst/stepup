package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectInfoRepository extends JpaRepository<SubjectInfo,Integer> {
}
