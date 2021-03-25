package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.repository.SubjectInfoRepository;
import net.ddns.varuzhan.demo.service.prototype.SubjectInfoService;
import org.springframework.stereotype.Component;

@Component
public class SubjectInfoServiceImpl implements SubjectInfoService {
    private final SubjectInfoRepository subjectInfoRepository;

    public SubjectInfoServiceImpl(SubjectInfoRepository subjectInfoRepository) {
        this.subjectInfoRepository = subjectInfoRepository;
    }

    @Override
    public SubjectInfo save(SubjectInfo subjectInfo) {
        return subjectInfoRepository.save(subjectInfo);
    }
}
