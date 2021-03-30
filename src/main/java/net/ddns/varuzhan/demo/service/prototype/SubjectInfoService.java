package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface SubjectInfoService {
    SubjectInfo save(SubjectInfo subjectInfo);
    SubjectInfo getSubjectInfoBySubjectName(String subjectName);
    Set<SubjectInfo> getAllSubjectInfos();
    SubjectInfo getSubjectInfoById(String id);
}
