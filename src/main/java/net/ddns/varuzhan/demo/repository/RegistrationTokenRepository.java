package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken,Integer> {
    Optional<RegistrationToken> findRegistrationTokenByToken(String token);
}
