package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.SubjectInfo;
import net.ddns.varuzhan.demo.repository.SubjectInfoRepository;
import net.ddns.varuzhan.demo.service.prototype.SubjectInfoService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    @Override
    public SubjectInfo getSubjectInfoBySubjectName(String subjectName) {
        return subjectInfoRepository.findSubjectInfoBySubjectName(subjectName).orElse(null);
    }

    @Override
    public Set<SubjectInfo> getAllSubjectInfos() {
        return new TreeSet<>(subjectInfoRepository.findAll());
    }

    @Override
    public SubjectInfo getSubjectInfoById(String id) {
        try{
            return subjectInfoRepository.findById(Integer.parseInt(id)).orElse(null);
        }
        catch (Exception e){
            return null;
        }

    }
}
