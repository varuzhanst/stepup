package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagersGroupsSubjectsRepository extends JpaRepository<ManagersGroupsSubjects,Integer> {
}
