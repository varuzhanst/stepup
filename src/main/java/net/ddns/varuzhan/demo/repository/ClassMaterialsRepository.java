package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ClassMaterialsRepository extends JpaRepository<ClassMaterial,Integer> {
    Set<ClassMaterial> findClassMaterialsByManagersGroupsSubjects(ManagersGroupsSubjects managersGroupsSubjects);
}
