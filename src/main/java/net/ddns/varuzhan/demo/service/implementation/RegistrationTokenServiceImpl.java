package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.RegistrationToken;
import net.ddns.varuzhan.demo.model.User;
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
        return registrationTokenRepository.findRegistrationTokenByToken(tokenUUID).orElse(null);
    }

    @Override
    public RegistrationToken save(RegistrationToken token) {
        return registrationTokenRepository.save(token);
    }

    @Override
    public Boolean isValidTokenAvailable(User user) {
        List<RegistrationToken> validToken = registrationTokenRepository.findAll()
                .stream()
                .filter(token -> LocalDateTime.now().isBefore(token.getExpiresAt()) && token.getUsedAt() == null && user.equals(token.getUser()))
                .collect(Collectors.toList());
        return !validToken.isEmpty();
    }


}
