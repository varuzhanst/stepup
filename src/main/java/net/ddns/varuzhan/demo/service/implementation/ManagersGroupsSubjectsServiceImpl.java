package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.ManagersGroupsSubjects;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.repository.ManagersGroupsSubjectsRepository;
import net.ddns.varuzhan.demo.service.prototype.ManagersGroupsSubjectsService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class ManagersGroupsSubjectsServiceImpl implements ManagersGroupsSubjectsService {
    private final ManagersGroupsSubjectsRepository managersGroupsSubjectsRepository;

    public ManagersGroupsSubjectsServiceImpl(ManagersGroupsSubjectsRepository managersGroupsSubjectsRepository) {
        this.managersGroupsSubjectsRepository = managersGroupsSubjectsRepository;
    }

    @Override
    public ManagersGroupsSubjects save(ManagersGroupsSubjects managersGroupsSubjects) {
        return managersGroupsSubjectsRepository.save(managersGroupsSubjects);
    }

    @Override
    public Set<ManagersGroupsSubjects> getAllManagersGroupsSubjects() {
        return new HashSet<>(managersGroupsSubjectsRepository.findAll());
    }

    @Override
    public Set<ManagersGroupsSubjects> getManagersGroupsUsersByManager(User user) {
        List<ManagersGroupsSubjects> all = managersGroupsSubjectsRepository.findAll();
        List<ManagersGroupsSubjects> collect1 = all.stream()
                .filter(x -> x.getUser().equals(user))
                .collect(Collectors.toList());
        return new HashSet<>(collect1);
    }
}
