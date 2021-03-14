package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken,Integer> {
}
