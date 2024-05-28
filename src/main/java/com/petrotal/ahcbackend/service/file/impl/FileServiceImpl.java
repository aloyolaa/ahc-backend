package com.petrotal.ahcbackend.service.file.impl;

import com.petrotal.ahcbackend.service.file.DataService;
import com.petrotal.ahcbackend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final DataService dataService;

    @Override
    public void processingFile(MultipartFile file) {
        dataService.manageData(file, "Diesel Operaciones");
        //dataService.manageData(file, "Gasolina Operaciones");
    }
}
