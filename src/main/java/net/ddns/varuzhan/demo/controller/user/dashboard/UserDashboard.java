package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.dto.SubjectsFilterDto;
import net.ddns.varuzhan.demo.dto.SubjectsIDsForFilterDto;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Controller
public class UserDashboard {
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final ClassMaterialService classMaterialService;
    private final AssignmentService assignmentService;
    private final SubjectInfoService subjectInfoService;
    private final AssignmentReturnedService assignmentReturnedService;
    private final ExamService examService;

    public UserDashboard(UserService userService, UserGroupInfoService userGroupInfoService, ManagersGroupsSubjectsService managersGroupsSubjectsService, ClassMaterialService classMaterialService, AssignmentService assignmentService, SubjectInfoService subjectInfoService, AssignmentReturnedService assignmentReturnedService, ExamService examService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.classMaterialService = classMaterialService;
        this.assignmentService = assignmentService;
        this.subjectInfoService = subjectInfoService;
        this.assignmentReturnedService = assignmentReturnedService;
        this.examService = examService;
    }

    @RequestMapping("/user/dashboard")
    public String loadUserDashboard(Model model, @ModelAttribute("selectedSubject") SubjectsFilterDto subjectsFilterDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = userService.getUserByEmail(authentication.getName()).getFirstName()
                + " " + userService.getUserByEmail(authentication.getName()).getMiddleName()
                + " " + userService.getUserByEmail(authentication.getName()).getLastName();
        UserGroupInfo groupInfoByUser = userGroupInfoService.getGroupInfoByUser(userService.getUserByEmail(authentication.getName()));
        if (groupInfoByUser == null) return "redirect:/login?noGroup";
        HashSet<ManagersGroupsSubjects> userAvailableManagersSubjects = (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagerSubjectByGroup(groupInfoByUser.getGroupInfo());
        TreeSet<ClassMaterial> userClassMaterials = new TreeSet<>();
        TreeSet<SubjectsIDsForFilterDto> userAvailableSubjects = new TreeSet<>();
        TreeSet<Exam> userAvailableExams = new TreeSet<>();
        userAvailableSubjects.add(new SubjectsIDsForFilterDto("-1", "Բոլոր առարկաները"));
        Set<Assignment> waitingForTurnIn = new TreeSet<>();
        Set<Assignment> deadlinePassedWaitingForTurnIn = new TreeSet<>();
        Set<AssignmentReturned> lateTurnedInAssignments = new TreeSet<>();
        Set<AssignmentReturned> turnedInAssignments = new TreeSet<>();
        Set<AssignmentReturned> returnedAssignments = new TreeSet<>();
        for (ManagersGroupsSubjects x : userAvailableManagersSubjects) {
            userAvailableExams = new TreeSet<>(examService.examsOfGroup(x.getGroupInfo())
                    .stream()
                    .filter(Exam::getPublished)
                    .collect(Collectors.toSet()));

            Set<ClassMaterial> setOfMaterials = classMaterialService.getMaterialsByManagerGroupSubject(x);
            userClassMaterials.addAll(setOfMaterials);
            userAvailableSubjects.add(new SubjectsIDsForFilterDto(x.getSubjectInfo().getId().toString(), x.getSubjectInfo().getSubjectName()));

            Set<Assignment> allUserAssignments = assignmentService.getAssignmentsByManagerGroupSubject(x);
            Set<AssignmentReturned> allUserAssignmentsReturned = assignmentReturnedService.getAssignmentReturnedByUser(userService.getUserByEmail(authentication.getName()));

            returnedAssignments = new TreeSet<>(allUserAssignmentsReturned.stream()
                    .filter(y -> y.getStatus() == AssignmentStatus.RETURNED)
                    .collect(Collectors.toSet()));
            turnedInAssignments = new TreeSet<>(allUserAssignmentsReturned.stream()
                    .filter(y -> y.getStatus() == AssignmentStatus.TURNED_IN)
                    .collect(Collectors.toSet()))
            ;
            lateTurnedInAssignments = new TreeSet<>(allUserAssignmentsReturned.stream()
                    .filter(y -> y.getStatus() == AssignmentStatus.LATE_TURNED_IN)
                    .collect(Collectors.toSet()));

            for (AssignmentReturned y : allUserAssignmentsReturned) {
                allUserAssignments.remove(y.getAssignment());
            }
            for (Assignment y : allUserAssignments) {
                if (y.getDeadline().isAfter(LocalDateTime.now()))
                    waitingForTurnIn.add(y);
                else deadlinePassedWaitingForTurnIn.add(y);
            }


        }
        model.addAttribute("waitingAssignments", waitingForTurnIn);
        model.addAttribute("deadlinePassedAssignments", deadlinePassedWaitingForTurnIn);
        model.addAttribute("turnedInAssignments", turnedInAssignments);
        model.addAttribute("lateTurnedInAssignments", lateTurnedInAssignments);
        model.addAttribute("returnedAssignments", returnedAssignments);
        model.addAttribute("userExams",userAvailableExams);

        if (subjectsFilterDto.getSubjectId() == null || subjectsFilterDto.getSubjectId().equals("-1")) {
            subjectsFilterDto.setSubjectId("-1");
            model.addAttribute("userMaterials", userClassMaterials);
            model.addAttribute("availSubjects", userAvailableSubjects);
            model.addAttribute("subjectFilter", subjectsFilterDto);
        } else {
            userClassMaterials.clear();
            for (ManagersGroupsSubjects x : userAvailableManagersSubjects) {
                if (x.getSubjectInfo().equals(subjectInfoService.getSubjectInfoById(subjectsFilterDto.getSubjectId()))) {
                    Set<ClassMaterial> setOfMaterials = classMaterialService.getMaterialsByManagerGroupSubject(x);
                    userClassMaterials.addAll(setOfMaterials);
                }
                userAvailableSubjects.add(new SubjectsIDsForFilterDto(x.getSubjectInfo().getId().toString(), x.getSubjectInfo().getSubjectName()));
                model.addAttribute("userMaterials", userClassMaterials);
                model.addAttribute("availSubjects", userAvailableSubjects);
                model.addAttribute("subjectFilter", subjectsFilterDto);
            }
        }
        String groupNumber = " (" + groupInfoByUser.getGroupInfo().getGroupNumber() + ")";
        model.addAttribute("full_name_group_number", fullName + groupNumber);
        return "user/dashboard/userDashboard";
    }


}
