package com.petrotal.ahcbackend.service.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    void processingFile(MultipartFile file);
}
