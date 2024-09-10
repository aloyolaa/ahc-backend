package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.exception.FileStorageException;
import com.petrotal.ahcbackend.service.data.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public String storeFile(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(uploadDir + fileName);
        try {
            Files.copy(file.getInputStream(), filePath);
            return fileName;
        } catch (IOException e) {
            throw new FileStorageException("Error guardando el archivo.");
        }
    }

    @Override
    public byte[] loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Error leyendo el archivo: " + fileName);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(fileName).normalize();
            Files.delete(filePath);
        } catch (IOException e) {
            throw new FileStorageException("Error al eliminar el archivo: " + fileName);
        }
    }
}
