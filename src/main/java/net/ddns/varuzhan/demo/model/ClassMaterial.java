package net.ddns.varuzhan.demo.model;

import com.sun.istack.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class ClassMaterial implements Comparable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String materialName;
    @ManyToOne
    @Nullable
    private File file;
    @ManyToOne
    private ManagersGroupsSubjects managersGroupsSubjects;
    private LocalDateTime localDateTime;

    public ClassMaterial() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ManagersGroupsSubjects getManagersGroupsSubjects() {
        return managersGroupsSubjects;
    }

    public void setManagersGroupsSubjects(ManagersGroupsSubjects managersGroupsSubjects) {
        this.managersGroupsSubjects = managersGroupsSubjects;
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
    public String getAddedAt(){
        return DateTimeFormatter.ISO_LOCAL_DATE.format(this.localDateTime);
    }
    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public int compareTo(Object o) {
        return this.id-((ClassMaterial)o).getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassMaterial that = (ClassMaterial) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
