package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ClassMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassMaterialsRepository extends JpaRepository<ClassMaterial,Integer> {
}
