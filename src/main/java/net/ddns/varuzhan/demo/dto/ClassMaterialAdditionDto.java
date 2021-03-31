package net.ddns.varuzhan.demo.dto;
import org.springframework.web.multipart.MultipartFile;


public class ClassMaterialAdditionDto {
    private String materialName;
    private MultipartFile materialFile;

    public ClassMaterialAdditionDto() {
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public MultipartFile getFile() {
        return materialFile;
    }

    public void setFile(MultipartFile materialFile) {
        this.materialFile = materialFile;
    }
}
