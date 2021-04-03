package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.fileupload.FileUploadUtil;
import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
@RequestMapping
public class AssignmentsTurnInController {
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final FileService fileService;
    private final AssignmentService assignmentService;
    private final AssignmentReturnedService assignmentReturnedService;

    public AssignmentsTurnInController(UserService userService, UserGroupInfoService userGroupInfoService, FileService fileService, AssignmentService assignmentService, AssignmentReturnedService assignmentReturnedService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.fileService = fileService;
        this.assignmentService = assignmentService;
        this.assignmentReturnedService = assignmentReturnedService;
    }

    @PostMapping("/user/dashboard/assignments/{assignmentId}/turnIn")
    public String turnInAssignment(@RequestParam("attachedFile")MultipartFile attachedFile, @PathVariable String assignmentId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                try {
                    File file = new File();
                    file.setAddedBy(user);
                    file.setFileName(StringUtils.cleanPath(attachedFile.getOriginalFilename()));
                    String uploadDir = "user_files/" + user.getId() + "/assignment/" + assignmentById.getId() + "/attached_file";
                    file.setFilePath(uploadDir);
                    FileUploadUtil.saveFile(uploadDir, file.getFileName(), attachedFile);
                    file=fileService.saveFile(file);
                    AssignmentReturned assignmentReturned = new AssignmentReturned();
                    assignmentReturned.setAssignment(assignmentById);
                    assignmentReturned.setFile(file);
                    assignmentReturned.setTurnedInBy(user);
                    assignmentReturned.setTurnedInAt(LocalDateTime.now());
                    if(assignmentById.getDeadline().isAfter(LocalDateTime.now()))
                        assignmentReturned.setStatus(AssignmentStatus.TURNED_IN);
                    else assignmentReturned.setStatus(AssignmentStatus.LATE_TURNED_IN);
                    assignmentReturnedService.save(assignmentReturned);
                } catch (Exception e) {
                    return "redirect:/error";
                }
                return "redirect:/user/dashboard";
            }
        }
        return "redirect:/error";
    }
}
