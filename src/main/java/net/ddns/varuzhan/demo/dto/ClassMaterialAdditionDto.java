package net.ddns.varuzhan.demo.dto;
import org.springframework.web.multipart.MultipartFile;


public class ClassMaterialAdditionDto {
    private String materialName;
    private MultipartFile file;

    public ClassMaterialAdditionDto() {
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
