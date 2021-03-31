package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.repository.ClassMaterialsRepository;
import net.ddns.varuzhan.demo.service.prototype.ClassMaterialService;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ClassMaterialServiceImpl implements ClassMaterialService {
    private final ClassMaterialsRepository classMaterialsRepository;

    public ClassMaterialServiceImpl(ClassMaterialsRepository classMaterialsRepository) {
        this.classMaterialsRepository = classMaterialsRepository;
    }

    @Override
    public ClassMaterial save(ClassMaterial classMaterial) {
        return classMaterialsRepository.save(classMaterial);
    }

    @Override
    public Set<ClassMaterial> getMaterialsByManagerGroupSubject(ManagersGroupsSubjects managersGroupsSubjects) {
        return classMaterialsRepository.findClassMaterialsByManagersGroupsSubjects(managersGroupsSubjects);
    }
}
