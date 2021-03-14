package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User save(User user);
}
