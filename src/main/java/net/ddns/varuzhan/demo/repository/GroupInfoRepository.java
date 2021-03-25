package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.GroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupInfoRepository extends JpaRepository<GroupInfo,Integer> {
}
