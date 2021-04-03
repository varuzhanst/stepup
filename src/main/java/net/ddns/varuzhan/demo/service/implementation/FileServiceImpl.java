package net.ddns.varuzhan.demo.service.implementation;

import net.ddns.varuzhan.demo.model.File;
import net.ddns.varuzhan.demo.repository.FileRepository;
import net.ddns.varuzhan.demo.service.prototype.FileService;
import org.springframework.stereotype.Component;

@Component
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File saveFile(File file) {
       return fileRepository.save(file);
    }
}
