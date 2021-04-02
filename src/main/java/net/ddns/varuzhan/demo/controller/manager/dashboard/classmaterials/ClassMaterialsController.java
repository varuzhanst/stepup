package net.ddns.varuzhan.demo.controller.manager.dashboard.classmaterials;

import net.ddns.varuzhan.demo.dto.ClassMaterialAdditionDto;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

    public ClassMaterialsController(ManagersGroupsSubjectsService managersGroupsSubjectsService, UserService userService, GroupInfoService groupInfoService, SubjectInfoService subjectInfoService, ClassMaterialService classMaterialService, FileService fileService) {
        this.managersGroupsSubjectsService = managersGroupsSubjectsService;
        this.userService = userService;
        this.groupInfoService = groupInfoService;
        this.subjectInfoService = subjectInfoService;
        this.classMaterialService = classMaterialService;
        this.fileService = fileService;
    }

    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects")
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
        return "manager/dashboard/classMaterials/classMaterials_subjectsOfGroup";
    }

    @GetMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects/{subjectId}")
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
        TreeSet<ClassMaterial> classMaterials = new TreeSet<>(classMaterialService.getMaterialsByManagerGroupSubject(managerGroupSubject)) ;

        model.addAttribute("group", groupInfoById);
        model.addAttribute("subject", subjectInfoById);
        model.addAttribute("newMaterial", new ClassMaterialAdditionDto());
        model.addAttribute("classMaterials", classMaterials);
        return "manager/dashboard/classMaterials/classMaterialsView";
    }

    @PostMapping("/manager/dashboard/classMaterials/groups/{groupId}/subjects/{subjectId}")
    public String loadGroupsPage(Model model, @PathVariable String groupId, @PathVariable String subjectId,@RequestParam("materialFile") MultipartFile materialFile, @ModelAttribute ClassMaterialAdditionDto newClassMaterial ) {
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
    try{
        File file = new File();
        file.setAddedBy(user);
        file.setFileName(StringUtils.cleanPath(materialFile.getOriginalFilename()));
        String uploadDir = "user_files/" + user.getId()+"/classMaterials/"+classMaterial.getId();
        file.setFilePath(uploadDir);
        FileUploadUtil.saveFile(uploadDir, file.getFileName(), materialFile);
        fileService.saveFile(file);
        classMaterial.setFile(file);
        classMaterialService.save(classMaterial);
    } catch (Exception e){
        return "redirect:/error";
    }
        return "redirect:/manager/dashboard/classMaterials/groups/"+groupId+"/subjects/"+ subjectId;
    }

    @GetMapping("/manager/dashboard/classMaterials/{materialId}/remove")
    public String removeClassMaterial(@PathVariable String materialId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
            classMaterialService.removeClassMaterial(materialById);
            return "redirect:/manager/dashboard/classMaterials/groups/"+materialById.getManagersGroupsSubjects().getGroupInfo().getId()+"/subjects/"+materialById.getManagersGroupsSubjects().getSubjectInfo().getId();

        }
        else return "redirect:/error";
    }
}
