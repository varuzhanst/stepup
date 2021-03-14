package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.UserData;
import net.ddns.varuzhan.demo.repository.UserDataRepository;
import net.ddns.varuzhan.demo.service.prototype.UserDataService;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserDataServiceImpl implements UserDataService {
    private final UserDataRepository userDataRepository;

    public UserDataServiceImpl(UserDataRepository userDataRepository) {
        this.userDataRepository = userDataRepository;
    }

    public UserData save(UserData person){
        return userDataRepository.save(person);
    }


    public UserData findUserDataByEmail(String email){
        Set<UserData> all = new HashSet<>(userDataRepository.findAll());
        for(UserData person: all){
            if (person.getEmail().equals(email))
                return userDataRepository.findById(person.getId()).get();
        }
        return null;
    }


}
