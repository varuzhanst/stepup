package net.ddns.varuzhan.demo.controller;

import net.ddns.varuzhan.demo.model.*;
import net.ddns.varuzhan.demo.service.prototype.ClassMaterialService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
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

    public FileDownloadController(UserService userService, UserGroupInfoService userGroupInfoService, ClassMaterialService classMaterialService) {
        this.userService = userService;
        this.userGroupInfoService = userGroupInfoService;
        this.classMaterialService = classMaterialService;
    }

    @GetMapping("/user/dashboard/classMaterials/{materialId}/downloadAttachedFile")
    public void returnFileToStudent(@PathVariable String materialId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
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

    @GetMapping("/manager/dashboard/classMaterials/{materialId}/downloadAttachedFile")
    public void returnFileToManager(@PathVariable String materialId, HttpServletResponse httpServletResponse) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User manager = userService.getUserByEmail(authentication.getName());
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
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
