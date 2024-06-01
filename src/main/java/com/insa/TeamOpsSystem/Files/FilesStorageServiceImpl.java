package com.insa.TeamOpsSystem.Files;

import com.insa.TeamOpsSystem.exceptions.FileNotExistsException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root;

    @Autowired
    public FilesStorageServiceImpl(StorageProperties properties) {
        this.root = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            java.nio.file.Files.createDirectory(root);
        } catch (IOException e) {
            throw new FileNotExistsException(e.getMessage());
        }
    }

    @Override
    public Object save(MultipartFile file, MultipartFile file2) {
        try {
            String lastFileName = file.getOriginalFilename();
            String lastFileName2 = file2.getOriginalFilename();

            java.nio.file.Files.copy(file.getInputStream(), this.root.resolve(Objects.requireNonNull(lastFileName)), StandardCopyOption.REPLACE_EXISTING);

            java.nio.file.Files.copy(file2.getInputStream(), this.root.resolve(Objects.requireNonNull(lastFileName2)), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileNotExistsException(e.getMessage());
        }
        return null;
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists()) {
                if (resource.isReadable()) {
                    return resource;
                } else {
                    throw new FileNotExistsException(filename + " Is not readable!");
                }
            } else {
                throw new FileNotExistsException(filename + " Is not found!");
            }
        } catch (MalformedURLException e) {
            throw new FileNotExistsException(filename + e.getMessage());
        }
    }

    @Override
    public List<Resource> loadFile(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @SneakyThrows
    @Override
    public void deleteByFileName(String filename) {
        FileSystemUtils.deleteRecursively(root.resolve(filename));
//        return filename;
    }


    @Override
    public Stream<Path> loadAll() {
        try {
            return java.nio.file.Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
