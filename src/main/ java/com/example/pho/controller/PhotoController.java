package com.example.pho.controller;

import com.example.pho.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    @Autowired
    private StorageService storageService;

    @GetMapping("/list")
    public List<String> listFiles(@RequestParam(defaultValue = "/") String path) {
        return storageService.listFiles(path);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             @RequestParam("path") String path) {
        try {
            storageService.uploadFile(path, file.getInputStream());
            return "Uploaded successfully";
        } catch (IOException e) {
            return "Upload failed: " + e.getMessage();
        }
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> download(@RequestParam String path) {
        var stream = storageService.downloadFile(path);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(stream));
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String path) {
        storageService.deleteFile(path);
        return "Deleted";
    }
}
