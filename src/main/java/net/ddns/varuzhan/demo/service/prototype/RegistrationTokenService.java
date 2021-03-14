package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.RegistrationToken;
import net.ddns.varuzhan.demo.model.User;
import org.springframework.stereotype.Service;


@Service
public interface RegistrationTokenService {
    RegistrationToken findTokenByTokenUUID(String token);
    RegistrationToken save(RegistrationToken token);
    Boolean isValidTokenAvailable(User userData);

}
