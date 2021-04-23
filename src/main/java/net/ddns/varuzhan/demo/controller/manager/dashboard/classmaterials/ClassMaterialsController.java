package net.ddns.varuzhan.demo.controller.manager.dashboard.classmaterials;

import net.ddns.varuzhan.demo.dto.ClassMaterialAdditionDto;
import net.ddns.varuzhan.demo.dto.ClassMaterialsShowAndQuizCountDto;
import net.ddns.varuzhan.demo.dto.NewQuestionDto;
import net.ddns.varuzhan.demo.dto.NewQuizQuestionDto;
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
public class ClassMaterialsController {
    private final ManagersGroupsSubjectsService managersGroupsSubjectsService;
    private final UserService userService;
    private final GroupInfoService groupInfoService;
    private final SubjectInfoService subjectInfoService;
    private final ClassMaterialService classMaterialService;
    private final FileService fileService;
    private final QuizQuestionService quizQuestionService;

    public ClassMaterialsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, ClassMaterialService classMaterialService, FileService fileService, QuizQuestionService quizQuestionService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.classMaterialService = classMaterialService;
        this.fileService = fileService;
        this.quizQuestionService = quizQuestionService;
    }

    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects")
    public String loadSubjectsPage(Model model, @PathVariable String groupId) {
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
        return "manager/dashboard/classMaterials/classMaterials_subjectsOfGroup";
    }

    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects/{subjectId}")
    public String loadClassMaterialsOfGroup(Model model, @PathVariable String groupId, @PathVariable String subjectId) {
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
        HashSet<ClassMaterial> classMaterials = new HashSet<>(classMaterialService.getMaterialsByManagerGroupSubject(managerGroupSubject));
        TreeSet<ClassMaterialsShowAndQuizCountDto> classMaterialsShowAndQuizCountDto = new TreeSet<>();
        for (ClassMaterial x : classMaterials) {
            classMaterialsShowAndQuizCountDto.add(new ClassMaterialsShowAndQuizCountDto(x, quizQuestionService.getQuestionsByClassMaterial(x).size()));
        }

        model.addAttribute("group", groupInfoById);
        model.addAttribute("subject", subjectInfoById);
        model.addAttribute("newMaterial", new ClassMaterialAdditionDto());
        model.addAttribute("classMaterialsAndQuestions", classMaterialsShowAndQuizCountDto);
        return "manager/dashboard/classMaterials/classMaterialsView";
    }


    @GetMapping("/manager/dashboard/classMaterials/{materialId}/questions")
    public String loadQuestionsOfClassMaterial(Model model, @PathVariable String materialId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName() + " " + user.getMiddleName() + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById == null) return "redirect:/error";
        if (!user.equals(materialById.getManagersGroupsSubjects().getUser())) return "redirect:/error";

        TreeSet<QuizQuestion> quizQuestions = new TreeSet<>(quizQuestionService.getQuestionsByClassMaterial(materialById));

        model.addAttribute("newQuestion", new NewQuizQuestionDto());
        model.addAttribute("classMaterial", materialById);
        model.addAttribute("questions", quizQuestions);
        return "manager/dashboard/classMaterials/quizQuestionsView";
    }
    @PostMapping("/manager/dashboard/classMaterials/{materialId}/questions")
    public String AddQuestionsToClassMaterial(@PathVariable String materialId, @ModelAttribute NewQuizQuestionDto newQuestionDto, @RequestParam("correct") String correctAnswer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById == null) return "redirect:/error";
        if (!user.equals(materialById.getManagersGroupsSubjects().getUser())) return "redirect:/error";
        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(newQuestionDto.getQuestionText());
        question.setClassMaterial(materialById);
        question.setOption1text(newQuestionDto.getOption1());
        question.setOption2text(newQuestionDto.getOption2());
        question.setOption3text(newQuestionDto.getOption3());
        question.setOption4text(newQuestionDto.getOption4());
        question.setCorrectOption(Integer.parseInt(correctAnswer));
        question.setNotes(newQuestionDto.getNotes());
        quizQuestionService.save(question);
        return "redirect:/manager/dashboard/classMaterials/"+materialId+"/questions";

    }

    @PostMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects/{subjectId}")
    public String newClassMaterialAddition(Model model, @PathVariable String groupId, @PathVariable String subjectId, @RequestParam("materialFile") MultipartFile materialFile, @ModelAttribute ClassMaterialAdditionDto newClassMaterial) {
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
        ClassMaterial classMaterial = new ClassMaterial();
        classMaterial.setMaterialName(newClassMaterial.getMaterialName());
        classMaterial.setLocalDateTime(LocalDateTime.now());
        classMaterial.setManagersGroupsSubjects(managerGroupSubject);
        classMaterial = classMaterialService.save(classMaterial);
        try {
            File file = new File();
            file.setAddedBy(user);
            file.setFileName(StringUtils.cleanPath(materialFile.getOriginalFilename()));
            String uploadDir = "user_files/" + user.getId() + "/classMaterials/" + classMaterial.getId();
            file.setFilePath(uploadDir);
            FileUploadUtil.saveFile(uploadDir, file.getFileName(), materialFile);
            fileService.saveFile(file);
            classMaterial.setFile(file);
            classMaterialService.save(classMaterial);
        } catch (Exception e) {
            return "redirect:/error";
        }
        return "redirect:/manager/dashboard/classMaterials/groups/" + groupId + "/subjects/" + subjectId;
    }

    @GetMapping("/manager/dashboard/classMaterials/{materialId}/remove")
    public String removeClassMaterial(@PathVariable String materialId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
            classMaterialService.removeClassMaterial(materialById);
            Path rootPath = Paths.get(materialById.getFile().getFilePath());
            try {
                Files.walk(rootPath, FileVisitOption.FOLLOW_LINKS)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(java.io.File::delete);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "redirect:/manager/dashboard/classMaterials/groups/" + materialById.getManagersGroupsSubjects().getGroupInfo().getId() + "/subjects/" + materialById.getManagersGroupsSubjects().getSubjectInfo().getId();

        } else return "redirect:/error";
    }
}
