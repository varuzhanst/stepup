package net.ddns.varuzhan.demo.service.prototype;

import net.ddns.varuzhan.demo.model.File;
import org.springframework.stereotype.Service;

@Service
public interface FileService {
    void saveFile(File file);
}
