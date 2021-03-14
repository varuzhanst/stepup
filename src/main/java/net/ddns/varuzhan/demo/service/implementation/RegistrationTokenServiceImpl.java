package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.RegistrationToken;
import net.ddns.varuzhan.demo.model.UserData;
import net.ddns.varuzhan.demo.repository.RegistrationTokenRepository;
import net.ddns.varuzhan.demo.service.prototype.RegistrationTokenService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RegistrationTokenServiceImpl implements RegistrationTokenService {
    private final RegistrationTokenRepository registrationTokenRepository;

    public RegistrationTokenServiceImpl(RegistrationTokenRepository registrationTokenRepository) {
        this.registrationTokenRepository = registrationTokenRepository;
    }

    @Override
    public RegistrationToken findTokenByTokenUUID(String tokenUUID) {
        List<RegistrationToken> tokens = registrationTokenRepository.findAll();
        for (RegistrationToken token : tokens) {
            if (token.getToken().equals(tokenUUID)) {
                return registrationTokenRepository.findById(token.getId()).get();
            }
        }
        return null;
    }

    @Override
    public RegistrationToken save(RegistrationToken token) {
        return registrationTokenRepository.save(token);
    }

    @Override
    public Boolean isValidTokenAvailable(UserData userData) {
        List<RegistrationToken> validToken = registrationTokenRepository.findAll()
                .stream()
                .filter(token -> LocalDateTime.now().isBefore(token.getExpiresAt()) && token.getUsedAt() == null && userData.equals(token.getUserData()))
                .collect(Collectors.toList());
        return !validToken.isEmpty();
    }


}
