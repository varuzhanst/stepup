package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.UserRepository;
import net.ddns.varuzhan.demo.service.prototype.UserService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(email + " էլ․ հասցեով օգտատեր գրանցված չէ։ Դիմեք համալասարանի ադմինիստրատորին"));
    }
}
