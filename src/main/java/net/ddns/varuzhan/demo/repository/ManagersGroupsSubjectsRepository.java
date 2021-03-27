package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ManagersGroupsSubjectsRepository extends JpaRepository<ManagersGroupsSubjects,Integer> {

}
