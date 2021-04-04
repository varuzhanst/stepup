package net.ddns.varuzhan.demo.controller.manager.dashboard.exams;

import net.ddns.varuzhan.demo.dto.ClassMaterialAdditionDto;
import net.ddns.varuzhan.demo.dto.ExamAdditionDto;
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
import java.util.TreeSet;


@Controller
public class ExamsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final SubjectInfoService subjectInfoService;
    private final ExamService examService;

    public ExamsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, ExamService examService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.examService = examService;
    }

    @GetMapping("/manager/dashboard/exams/groups/{groupId}/subjects")
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
        return "manager/dashboard/exams/exams_subjectsOfGroup";
    }

    @GetMapping("/manager/dashboard/exams/groups/{groupId}/subjects/{subjectId}")
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
        TreeSet<Exam> exams = new TreeSet<>(examService.examsOfManager(managerGroupSubject));

        model.addAttribute("group", groupInfoById);
        model.addAttribute("subject", subjectInfoById);
        model.addAttribute("newExam", new ExamAdditionDto());
        model.addAttribute("exams", exams);
        return "manager/dashboard/exams/examsView";
    }

    @PostMapping("/manager/dashboard/exams/groups/{groupId}/subjects/{subjectId}")
    public String loadGroupsPage(Model model, @PathVariable String groupId, @PathVariable String subjectId, @ModelAttribute ExamAdditionDto examAdditionDto ) {
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
    try{
        Exam exam = new Exam(
                LocalDateTime.now(),
                LocalDateTime.parse(examAdditionDto.getStartAt()),
                Integer.parseInt(examAdditionDto.getDuration()),
                Integer.parseInt(examAdditionDto.getMaxGrade()),
                false,
                managerGroupSubject,
                examAdditionDto.getExamName(),
                Integer.parseInt(examAdditionDto.getQuestionsCount()),
                Integer.parseInt(examAdditionDto.getChoicesCount())
        );
        examService.save(exam);
    } catch (Exception e){
        return "redirect:/error";
    }
        return "redirect:/manager/dashboard/exams/"+groupId+"/subjects/"+ subjectId;
    }

    @GetMapping("/manager/dashboard/exams/{examId}/remove")
    public String removeClassMaterial(@PathVariable String examId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Exam examById = examService.getExamById(examId);
        if (examById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
            examService.removeExam(examById);
            return "redirect:/manager/dashboard/exams/groups/"+examById.getManagersGroupsSubjects().getGroupInfo().getId()+"/subjects/"+examById.getManagersGroupsSubjects().getSubjectInfo().getId();

        }
        else return "redirect:/error";
    }
}
