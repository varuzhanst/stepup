package net.ddns.varuzhan.demo.controller;

import net.ddns.varuzhan.demo.dto.UserManagerRegistrationDto;
import net.ddns.varuzhan.demo.model.UserData;
import net.ddns.varuzhan.demo.model.RegistrationToken;
import net.ddns.varuzhan.demo.model.Role;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.service.prototype.UserDataService;
import net.ddns.varuzhan.demo.service.prototype.RegistrationTokenService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Controller
@RequestMapping("/registration")
public class GlobalRegistrationController {
    private final UserDataService userDataService;
    private final RegistrationTokenService registrationTokenService;
    private final UserService userService;

    public GlobalRegistrationController(UserDataService userDataService, RegistrationTokenService registrationTokenService, UserService userService) {
        this.userDataService = userDataService;
        this.registrationTokenService = registrationTokenService;
        this.userService = userService;
    }


    @GetMapping()
    String loadUserData(@RequestParam(required = false) String token, Model model) {
        if (token == null) {
            model.addAttribute("userInfo", new UserManagerRegistrationDto());
            return "registration1";
        } else {
            RegistrationToken registrationToken = registrationTokenService.findTokenByTokenUUID(token);
            if (registrationToken == null || LocalDateTime.now().isAfter(registrationToken.getExpiresAt()) || registrationToken.getUsedAt()!=null) {
                return "redirect:/registration?invalidToken";
            } else {
                UserManagerRegistrationDto possibleUserManager = new UserManagerRegistrationDto();
                possibleUserManager.setFirstName(registrationToken.getUserData().getFirstName());
                possibleUserManager.setMiddleName(registrationToken.getUserData().getMiddleName());
                possibleUserManager.setLastName(registrationToken.getUserData().getLastName());
                possibleUserManager.setEmail(registrationToken.getUserData().getEmail());
                possibleUserManager.setToken(token);
                if (registrationToken.getUserData().getRole() == Role.USER)
                    possibleUserManager.setRole("Ուսանող");
                else if (registrationToken.getUserData().getRole() == Role.MANAGER)
                    possibleUserManager.setRole("Դասախոս");
                model.addAttribute("userInfo", possibleUserManager);
                return "registration2";
            }
        }

    }

    @PostMapping
    String userCheckByEmail(@ModelAttribute(name = "userInfo") UserManagerRegistrationDto userManagerRegistrationDto) {
        String token=userManagerRegistrationDto.getToken();
        if (token == null) {
            UserData person = userDataService.findUserDataByEmail(userManagerRegistrationDto.getEmail());
            if (person == null || person.isRegistered() ) {
                return "redirect:/registration?error";
            } else {
                if(registrationTokenService.isValidTokenAvailable(person)) return "redirect:/registration?tokenLimitExceed";
                RegistrationToken registrationToken = new RegistrationToken();
                registrationToken.setToken(UUID.randomUUID().toString());
                registrationToken.setUserData(person);
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
                User user = new User();
                user.setPassword(userManagerRegistrationDto.getPassword());
                registrationToken.getUserData().setRegistered(true);
                userDataService.save(registrationToken.getUserData());
                user.setUserData(registrationToken.getUserData());
                registrationToken.setUsedAt(LocalDateTime.now());
                registrationTokenService.save(registrationToken);
                userService.save(user);
                return "redirect:/";
            }


        }

    }

}
