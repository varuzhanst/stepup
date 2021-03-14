package net.ddns.varuzhan.demo.controller;

import net.ddns.varuzhan.demo.dto.UserRegistrationDto;
import net.ddns.varuzhan.demo.model.RegistrationToken;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.service.prototype.RegistrationTokenService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Controller
@RequestMapping("/registration")
public class GlobalRegistrationController {
    private final RegistrationTokenService registrationTokenService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public GlobalRegistrationController(RegistrationTokenService registrationTokenService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.registrationTokenService = registrationTokenService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @GetMapping()
    String loadUserData(@RequestParam(required = false) String token, Model model) {
        if (token == null) {
            model.addAttribute("userInfo", new UserRegistrationDto());
            return "registration1";
        } else {
            RegistrationToken registrationToken = registrationTokenService.findTokenByTokenUUID(token);
            if (registrationToken == null || LocalDateTime.now().isAfter(registrationToken.getExpiresAt()) || registrationToken.getUsedAt()!=null) {
                return "redirect:/registration?invalidToken";
            } else {
                UserRegistrationDto possibleUserManager = new UserRegistrationDto();
                possibleUserManager.setFirstName(registrationToken.getUser().getFirstName());
                possibleUserManager.setMiddleName(registrationToken.getUser().getMiddleName());
                possibleUserManager.setLastName(registrationToken.getUser().getLastName());
                possibleUserManager.setEmail(registrationToken.getUser().getEmail());
                possibleUserManager.setToken(token);
                if (registrationToken.getUser().getRole() == Role.USER)
                    possibleUserManager.setRole("Ուսանող");
                else if (registrationToken.getUser().getRole() == Role.MANAGER)
                    possibleUserManager.setRole("Դասախոս");
                model.addAttribute("userInfo", possibleUserManager);
                return "registration2";
            }
        }

    }

    @PostMapping
    String userCheckByEmail(@ModelAttribute(name = "userInfo") UserRegistrationDto userManagerRegistrationDto) {
        String token=userManagerRegistrationDto.getToken();
        if (token == null) {
            User user = userService.getUserByEmail(userManagerRegistrationDto.getEmail());
            if (user == null || user.getEnabled()) {
                return "redirect:/registration?error";
            } else {
                if(registrationTokenService.isValidTokenAvailable(user)) return "redirect:/registration?tokenLimitExceed";
                RegistrationToken registrationToken = new RegistrationToken();
                registrationToken.setToken(UUID.randomUUID().toString());
                registrationToken.setUser(user);
                registrationToken.setCreatedAt(LocalDateTime.now());
                registrationToken.setExpiresAt(LocalDateTime.now().plusMinutes(60L));
                registrationTokenService.save(registrationToken);
                return "redirect:/registration?success";
            }

        } else {
            RegistrationToken registrationToken = registrationTokenService.findTokenByTokenUUID(token);
            if (registrationToken == null || LocalDateTime.now().isAfter(registrationToken.getExpiresAt()) || registrationToken.getUsedAt()!=null)
                return "redirect:/registration?invalidToken";
            else {
                User user = registrationToken.getUser();
                user.setPassword(bCryptPasswordEncoder.encode(userManagerRegistrationDto.getPassword()));
                registrationToken.getUser().setEnabled(true);
                userService.save(registrationToken.getUser());
                registrationToken.setUsedAt(LocalDateTime.now());
                registrationTokenService.save(registrationToken);
                userService.save(user);
                return "redirect:/";
            }


        }

    }

}
