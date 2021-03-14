package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.UserRepository;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
