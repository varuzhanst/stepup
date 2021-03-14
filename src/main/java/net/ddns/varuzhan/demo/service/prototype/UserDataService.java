package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.UserData;
import org.springframework.stereotype.Service;

@Service
public interface UserDataService {
    UserData save(UserData person);
    UserData findUserDataByEmail(String email);


}
