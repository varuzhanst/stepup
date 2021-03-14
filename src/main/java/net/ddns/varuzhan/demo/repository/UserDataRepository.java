package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,Integer> {
}
