package net.ddns.varuzhan.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class ClassMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String materialName;
    @ManyToOne
    private File file;
    @ManyToOne
    private ManagersGroupsSubjects managersGroupsSubjects;

    public ClassMaterial() {
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
}
