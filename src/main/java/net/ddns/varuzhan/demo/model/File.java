package net.ddns.varuzhan.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fileToken;
    private String filePath;
    @ManyToOne
    private User addedBy;

    public void setAddedBy(User addedBy) {
        this.addedBy = addedBy;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    private LocalDateTime addedAt;

    public File() {
    }

    public User getAddedBy() {
        return addedBy;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileToken() {
        return fileToken;
    }

    public void setFileToken(String fileToken) {
        this.fileToken = fileToken;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
