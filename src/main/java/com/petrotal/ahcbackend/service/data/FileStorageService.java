package com.petrotal.ahcbackend.service.data;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(MultipartFile file);

    byte[] loadFileAsResource(String fileName);

    void deleteFile(String fileName);
}
