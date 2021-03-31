package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface ClassMaterialService {
    Set<ClassMaterial> getMaterialsByManagerGroupSubject(ManagersGroupsSubjects managersGroupsSubjects);
}
