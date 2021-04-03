package net.ddns.varuzhan.demo.controller;

import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping
public class FileDownloadController {
    private final UserService userService;
    private final UserGroupInfoService userGroupInfoService;
    private final ClassMaterialService classMaterialService;
    private final AssignmentService assignmentService;
    private final AssignmentReturnedService assignmentReturnedService;

    public FileDownloadController(UserService userService, UserGroupInfoService userGroupInfoService, ClassMaterialService classMaterialService, AssignmentService assignmentService, AssignmentReturnedService assignmentReturnedService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.classMaterialService = classMaterialService;
        this.assignmentService = assignmentService;
        this.assignmentReturnedService = assignmentReturnedService;
    }

    @GetMapping("/user/dashboard/classMaterials/{materialId}/downloadAttachedFile")
    public void returnMaterialFileToStudent(@PathVariable String materialId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById != null) {
            if (materialById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                File fileInfo = materialById.getFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }

    @GetMapping("/user/dashboard/assignments/{assignmentId}/downloadAssignmentDescription")
    public void returnDescriptionFileToStudent(@PathVariable String assignmentId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getGroupInfo().equals(userGroupInfoService.getGroupInfoByUser(user).getGroupInfo())) {
                File fileInfo = assignmentById.getDescriptionFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }

    @GetMapping("/manager/dashboard/classMaterials/{materialId}/downloadAttachedFile")
    public void returnMaterialFileToManager(@PathVariable String materialId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        if (materialById != null) {
            if (materialById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                File fileInfo = materialById.getFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }

    @GetMapping("/manager/dashboard/assignments/{assignmentId}/downloadDescriptionFile")
    public void returnDescriptionFileToManager(@PathVariable String assignmentId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        if (assignmentById != null) {
            if (assignmentById.getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                File fileInfo = assignmentById.getDescriptionFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }

    @GetMapping("/manager/dashboard/assignments/{assignmentReturnedId}/downloadAttachedFile")
    public void returnAttachedFileToManager(@PathVariable String assignmentReturnedId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        AssignmentReturned assignmentReturnedById = assignmentReturnedService.getAssignmentReturnedById(assignmentReturnedId);
        if (assignmentReturnedById != null) {
            if (assignmentReturnedById.getAssignment().getManagersGroupsSubjects().getUser().equals(userService.getUserById(manager.getId().toString()))) {
                File fileInfo = assignmentReturnedById.getFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }
    @GetMapping("/user/dashboard/assignments/{assignmentReturnedId}/downloadAttachedFile")
    public void returnAttachedFileToUser (@PathVariable String assignmentReturnedId, HttpServletResponse
            httpServletResponse){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        AssignmentReturned assignmentReturnedById = assignmentReturnedService.getAssignmentReturnedById(assignmentReturnedId);
        if (assignmentReturnedById != null) {
            if (assignmentReturnedById.getTurnedInBy().equals(userService.getUserById(user.getId().toString()))) {
                File fileInfo = assignmentReturnedById.getFile();
                Path file = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
                if (Files.exists(file)) {
                    httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + fileInfo.getFileName());
                    try {
                        Files.copy(file, httpServletResponse.getOutputStream());
                        httpServletResponse.getOutputStream().flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }


    }
}