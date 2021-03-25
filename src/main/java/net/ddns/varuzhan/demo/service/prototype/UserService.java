package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Service
public interface UserService {
    User save(User user);
    User getUserByEmail(String email);
    Set<User> getAllManagers();
    Set<User> getAllUsers();
}
