package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.AdminRegisteredPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRegisteredPersonRepository extends JpaRepository<AdminRegisteredPerson,Integer> {
}
