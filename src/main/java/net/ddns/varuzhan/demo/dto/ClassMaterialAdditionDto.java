package net.ddns.varuzhan.demo.dto;

import net.ddns.varuzhan.demo.model.GroupInfo;
import net.ddns.varuzhan.demo.model.SubjectInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.TreeSet;

public class ClassMaterialAdditionDto {
    private String name;
    private TreeSet<GroupInfo> groups;
    private TreeSet<SubjectInfo> subjects;
    private MultipartFile file;

    public ClassMaterialAdditionDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<GroupInfo> getGroups() {
        return groups;
    }

    public void setGroups(TreeSet<GroupInfo> groups) {
        this.groups = groups;
    }

    public TreeSet<SubjectInfo> getSubjects() {
        return subjects;
    }

    public void setSubjects(TreeSet<SubjectInfo> subjects) {
        this.subjects = subjects;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
