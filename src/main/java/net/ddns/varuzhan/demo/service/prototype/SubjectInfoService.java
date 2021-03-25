package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import org.springframework.stereotype.Service;

@Service
public interface SubjectInfoService {
    SubjectInfo save(SubjectInfo subjectInfo);
}
