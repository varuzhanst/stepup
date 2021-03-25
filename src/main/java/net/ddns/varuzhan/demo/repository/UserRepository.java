package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Set<User> findAllByRole(Role role);
}
