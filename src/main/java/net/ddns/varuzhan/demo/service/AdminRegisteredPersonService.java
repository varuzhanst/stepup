package net.ddns.varuzhan.demo.service;

import net.ddns.varuzhan.demo.model.AdminRegisteredPerson;
import net.ddns.varuzhan.demo.repository.AdminRegisteredPersonRepository;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminRegisteredPersonService {
    private final AdminRegisteredPersonRepository adminRegisteredPersonRepository;

    public AdminRegisteredPersonService(AdminRegisteredPersonRepository adminRegisteredPersonRepository) {
        this.adminRegisteredPersonRepository = adminRegisteredPersonRepository;
    }



    public AdminRegisteredPerson save(AdminRegisteredPerson person){
        return adminRegisteredPersonRepository.save(person);
    }


    public HashSet<AdminRegisteredPerson> getAllAdminRegisteredUsers(){

        Set<AdminRegisteredPerson> AllStudents =
                        adminRegisteredPersonRepository.findAll()
                        .stream()
                        .filter((AdminRegisteredPerson person)->person.getRole().equals("STUDENT"))
                        .collect(Collectors.toSet());
        return new HashSet<>(AllStudents);
    }

    public HashSet<AdminRegisteredPerson> getAllAdminRegisteredManagers(){

        Set<AdminRegisteredPerson> AllStudents =
                adminRegisteredPersonRepository.findAll()
                        .stream()
                        .filter((AdminRegisteredPerson person)->person.getRole().equals("MANAGER"))
                        .collect(Collectors.toSet());
        return new HashSet<>(AllStudents);
    }
    public boolean isUserFoundByEmail(String email){
        Set<AdminRegisteredPerson> all = new HashSet<>(adminRegisteredPersonRepository.findAll());
        for(AdminRegisteredPerson person: all){
            if (person.getEmail().equals(email)) return true;
        }
        return false;
    }
}
