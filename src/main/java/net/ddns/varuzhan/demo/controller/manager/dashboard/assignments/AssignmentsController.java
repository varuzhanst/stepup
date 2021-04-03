package net.ddns.varuzhan.demo.controller.manager.dashboard.assignments;

import net.ddns.varuzhan.demo.dto.AssignmentAdditionDto;
import net.ddns.varuzhan.demo.dto.AssignmentReturnDto;
import net.ddns.varuzhan.demo.dto.AssignmentsShowDto;
import net.ddns.varuzhan.demo.fileupload.FileUploadUtil;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


@Controller
public class AssignmentsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final SubjectInfoService subjectInfoService;
    private final AssignmentService assignmentService;
    private final FileService fileService;
    private final AssignmentReturnedService assignmentReturnedService;
    private final UserGroupInfoService userGroupInfoService;

    public AssignmentsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, AssignmentService assignmentService, FileService fileService, AssignmentReturnedService assignmentReturnedService, UserGroupInfoService userGroupInfoService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.assignmentService = assignmentService;
        this.fileService = fileService;
        this.assignmentReturnedService = assignmentReturnedService;
        this.userGroupInfoService = userGroupInfoService;
    }

    @GetMapping("/manager/dashboard/assignments/groups/{groupId}/subjects")
    public String loadGroupsPage(Model model, @PathVariable String groupId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        if (groupInfoById == null) {
            return "redirect:/error";
        }
        HashSet<ManagersGroupsSubjects> subjects = (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagersGroupsAndSubjectsByGroupAndSubject(groupInfoById, user);
        TreeSet<SubjectInfo> subjectsOfGroup = new TreeSet<>();
        for (ManagersGroupsSubjects x : subjects) {
            subjectsOfGroup.add(x.getSubjectInfo());
        }
        TreeSet<GroupInfo> groupsOfManager = new TreeSet<>();
        HashSet<ManagersGroupsSubjects> groupsAndSubjectsOfManager = (HashSet<ManagersGroupsSubjects>) managersGroupsSubjectsService.getManagersGroupAndSubjectInfos(user);
        for (ManagersGroupsSubjects x : groupsAndSubjectsOfManager) {
            groupsOfManager.add(x.getGroupInfo());
        }
        model.addAttribute("group", groupInfoById);
        model.addAttribute("subjects", subjectsOfGroup);
        return "manager/dashboard/assignments/assignments_subjectsOfGroup";
    }

    @GetMapping("/manager/dashboard/assignments/groups/{groupId}/subjects/{subjectId}")
    public String loadGroupsPage(Model model, @PathVariable String groupId, @PathVariable String subjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);

        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        SubjectInfo subjectInfoById = subjectInfoService.getSubjectInfoById(subjectId);

        if (groupInfoById == null || subjectInfoById == null) {
            return "redirect:/error";
        }
        ManagersGroupsSubjects managerGroupSubject = managersGroupsSubjectsService.getManagerGroupSubjectByGroupManagerSubject(groupInfoById, user, subjectInfoById);
        if (managerGroupSubject == null) {
            return "redirect:/error";
        }
        HashSet<Assignment> assignments = new HashSet<>(assignmentService.getAssignmentsByManagerGroupSubject(managerGroupSubject));
        TreeSet<AssignmentsShowDto> assignmentsShowDtos = new TreeSet<>();
        for (Assignment x : assignments) {


            AssignmentsShowDto assignmentsShowDto = new AssignmentsShowDto(
                    x,
                    userGroupInfoService.getUsersByGroupInfo(x.getManagersGroupsSubjects().getGroupInfo()).size() - assignmentReturnedService.getReturnedAssignmentsByAssignment(x).size(),
                    (int) assignmentReturnedService.getReturnedAssignmentsByAssignment(x).stream()
                            .filter(y -> y.getStatus() == AssignmentStatus.TURNED_IN).count(),
                    (int) assignmentReturnedService.getReturnedAssignmentsByAssignment(x).stream()
                            .filter(y -> y.getStatus() == AssignmentStatus.LATE_TURNED_IN).count(),
                    (int) assignmentReturnedService.getReturnedAssignmentsByAssignment(x).stream()
                            .filter(y -> y.getStatus() == AssignmentStatus.RETURNED).count()
            );
            assignmentsShowDtos.add(assignmentsShowDto);
        }
        model.addAttribute("group", groupInfoById);
        model.addAttribute("subject", subjectInfoById);
        model.addAttribute("newAssignment", new AssignmentAdditionDto());
        model.addAttribute("assignments", assignmentsShowDtos);
        return "manager/dashboard/assignments/assignmentsView";
    }

    @PostMapping("/manager/dashboard/assignments/groups/{groupId}/subjects/{subjectId}")
    public String loadGroupsPage(Model model, @PathVariable String groupId, @PathVariable String subjectId, @RequestParam("descriptionFile") MultipartFile materialFile, @ModelAttribute() AssignmentAdditionDto assignmentAdditionDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);

        GroupInfo groupInfoById = groupInfoService.getGroupInfoById(groupId);
        SubjectInfo subjectInfoById = subjectInfoService.getSubjectInfoById(subjectId);

        if (groupInfoById == null || subjectInfoById == null || materialFile == null) {
            return "redirect:/error";
        }
        ManagersGroupsSubjects managerGroupSubject = managersGroupsSubjectsService.getManagerGroupSubjectByGroupManagerSubject(groupInfoById, user, subjectInfoById);
        if (managerGroupSubject == null) {
            return "redirect:/error";
        }
        Assignment assignment = new Assignment();
        assignment.setAssignmentName(assignmentAdditionDto.getAssignmentName());
        assignment.setAddedAt(LocalDateTime.now());
        assignment.setDeadline(assignmentAdditionDto.getDeadlineLocal());
        assignment.setManagersGroupsSubjects(managerGroupSubject);
        assignment.setMaxGrade(Integer.parseInt(assignmentAdditionDto.getMaxGrade()));
        assignment = assignmentService.save(assignment);

        try {
            File file = new File();
            file.setAddedBy(user);
            file.setFileName(StringUtils.cleanPath(materialFile.getOriginalFilename()));
            String uploadDir = "user_files/" + user.getId() + "/assignment/" + assignment.getId() + "/description_file";
            file.setFilePath(uploadDir);
            FileUploadUtil.saveFile(uploadDir, file.getFileName(), materialFile);
            fileService.saveFile(file);
            assignment.setDescriptionFile(file);
            assignmentService.save(assignment);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/manager/dashboard/assignments/groups/" + groupId + "/subjects/" + subjectId;
    }


    @GetMapping("/manager/dashboard/assignments/{assignmentId}/remove")
    public String removeClassMaterial(@PathVariable String assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                assignmentService.removeAssignment(assignmentById);
                Path rootPath = Paths.get(assignmentById.getDescriptionFile().getFilePath());
                try {
                    Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS)
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(java.io.File::delete);
                } catch (IOException e) {
                    return "redirect:/error";
                }
                return "redirect:/manager/dashboard/assignments/groups/"
                        + assignmentById.getManagersGroupsSubjects().getGroupInfo().getId()
                        + "/subjects/"
                        + assignmentById.getManagersGroupsSubjects().getSubjectInfo().getId();

            } else return "redirect:/error";
        }
        return "redirect:/error";
    }

    @GetMapping("/manager/dashboard/assignments/{assignmentId}/nonTurnedInUsers")
    public String getNonTurnedInUsers(Model model, @PathVariable String assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                TreeSet<User> nonTurnedInUsers = (TreeSet<User>) assignmentReturnedService.getAssignmentsNonTurnedIn(assignmentById);
                model.addAttribute("group", assignmentById.getManagersGroupsSubjects().getGroupInfo());
                model.addAttribute("full_name", manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName());
                model.addAttribute("subject", assignmentById.getManagersGroupsSubjects().getSubjectInfo());
                model.addAttribute("assignment", assignmentById);
                model.addAttribute("users", nonTurnedInUsers);
                return "manager/dashboard/assignments/nonTurnedIn/assignmentsNonTurnedIn";
            }
        }
        return "redirect:/error";
    }

    @GetMapping("/manager/dashboard/assignments/{assignmentId}/turnedInUsers")
    public String getTurnedInUsers(Model model, @PathVariable String assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                Set<AssignmentReturned> assignmentsTurnedIn = assignmentReturnedService.getAssignmentsTurnedIn(assignmentById);
                model.addAttribute("group", assignmentById.getManagersGroupsSubjects().getGroupInfo());
                model.addAttribute("full_name", manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName());
                model.addAttribute("subject", assignmentById.getManagersGroupsSubjects().getSubjectInfo());
                model.addAttribute("assignment", assignmentById);
                model.addAttribute("maxGrade", assignmentById.getMaxGrade());
                model.addAttribute("assignmentReturn", new AssignmentReturnDto());
                model.addAttribute("assignments", new HashSet<>(assignmentsTurnedIn));
                return "manager/dashboard/assignments/turnedIn/assignmentsTurnedIn";
            }
        }
        return "redirect:/error";
    }
    @GetMapping("/manager/dashboard/assignments/{assignmentId}/lateTurnedInUsers")
    public String getLateTurnedInUsers(Model model, @PathVariable String assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                Set<AssignmentReturned> assignmentsTurnedIn = assignmentReturnedService.getAssignmentsLateTurnedIn(assignmentById);
                model.addAttribute("group", assignmentById.getManagersGroupsSubjects().getGroupInfo());
                model.addAttribute("full_name", manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName());
                model.addAttribute("subject", assignmentById.getManagersGroupsSubjects().getSubjectInfo());
                model.addAttribute("assignment", assignmentById);
                model.addAttribute("maxGrade", assignmentById.getMaxGrade());
                model.addAttribute("assignmentReturn", new AssignmentReturnDto());
                model.addAttribute("assignments", new HashSet<>(assignmentsTurnedIn));
                return "manager/dashboard/assignments/lateTurnedIn/assignmentsTurnedIn";
            }
        }
        return "redirect:/error";
    }
    @GetMapping("/manager/dashboard/assignments/{assignmentId}/returned")
    public String getReturnedUsers(Model model, @PathVariable String assignmentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                Set<AssignmentReturned> assignmentsTurnedIn = assignmentReturnedService.getAssignmentsReturned(assignmentById);
                model.addAttribute("group", assignmentById.getManagersGroupsSubjects().getGroupInfo());
                model.addAttribute("full_name", manager.getFirstName() + " " + manager.getMiddleName() + " " + manager.getLastName());
                model.addAttribute("subject", assignmentById.getManagersGroupsSubjects().getSubjectInfo());
                model.addAttribute("assignment", assignmentById);
                model.addAttribute("maxGrade", assignmentById.getMaxGrade());
                model.addAttribute("assignments", new HashSet<>(assignmentsTurnedIn));
                return "manager/dashboard/assignments/returned/assignmentsTurnedIn";
            }
        }
        return "redirect:/error";
    }
    @PostMapping("/manager/dashboard/assignments/{turnedInAssignmentId}/return")
    public String turnInAssignment(@PathVariable String turnedInAssignmentId, @ModelAttribute("assignmentReturn") AssignmentReturnDto assignmentReturnDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        AssignmentReturned assignmentReturnedById = assignmentReturnedService.getAssignmentReturnedById(turnedInAssignmentId);
        if (assignmentReturnedById != null) {
            if (assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getUser().equals(userGroupInfoService.getGroupInfoByUser(user).getUser())) {
                try {
                    Integer actualGrade = Integer.parseInt(assignmentReturnDto.getGrade());
                    if (actualGrade > assignmentReturnedById.getAssignment().getMaxGrade() || actualGrade < 0) throw new Exception("Wrong grade");
                    else{
                        assignmentReturnedById.setStatus(AssignmentStatus.RETURNED);
                        assignmentReturnedById.setReturnedAt(LocalDateTime.now());
                        assignmentReturnedById.setActualGrade(actualGrade);
                        assignmentReturnedService.save(assignmentReturnedById);
                        return "redirect:/manager/dashboard/assignments/groups/"
                                + assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getGroupInfo().getId()
                                + "/subjects/"
                                + assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getSubjectInfo().getId();
                    }
                } catch (Exception e) {
                    return "redirect:/manager/dashboard/assignments/groups/"
                            + assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getGroupInfo().getId()
                            + "/subjects/"
                            + assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getSubjectInfo().getId()
                            + "?wrongGrade";
                }

            }
        }
        return "redirect:/error";
    }
}



