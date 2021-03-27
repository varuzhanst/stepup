package net.ddns.varuzhan.demo.repository;

import net.ddns.varuzhan.demo.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Integer> {
}
