package com.example.pho.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalStorageService implements StorageService {

    private static final Logger log = LoggerFactory.getLogger(LocalStorageService.class);

    @Value("${storage.local.base-path:./photos}")
    private String basePath;

    private Path getBasePath() {
        return Paths.get(basePath).toAbsolutePath().normalize();
    }

    private Path resolvePath(String path) {
        return getBasePath().resolve(path).normalize();
    }

    @Override
    public List<String> listFiles(String path) {
        try {
            Path dir = resolvePath(path);
            if (!Files.exists(dir)) {
                return List.of();
            }
            try (var stream = Files.list(dir)) {
                return stream.map(p -> p.getFileName().toString()).collect(Collectors.toList());
            }
        } catch (IOException e) {
            log.error("Failed to list files in {}", path, e);
            return List.of();
        }
    }

    @Override
    public void uploadFile(String targetPath, InputStream inputStream) {
        try {
            Path target = resolvePath(targetPath);
            Files.createDirectories(target.getParent());
            Files.copy(inputStream, target, StandardCopyOption.REPLACE_EXISTING);
            log.info("Uploaded file to {}", target);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + targetPath, e);
        }
    }

    @Override
    public InputStream downloadFile(String path) {
        try {
            Path file = resolvePath(path);
            if (!Files.exists(file)) {
                throw new FileNotFoundException("File not found: " + path);
            }
            return Files.newInputStream(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to download file: " + path, e);
        }
    }

    @Override
    public boolean exists(String path) {
        return Files.exists(resolvePath(path));
    }

    @Override
    public void deleteFile(String path) {
        try {
            Files.deleteIfExists(resolvePath(path));
            log.info("Deleted file {}", path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file: " + path, e);
        }
    }
}
