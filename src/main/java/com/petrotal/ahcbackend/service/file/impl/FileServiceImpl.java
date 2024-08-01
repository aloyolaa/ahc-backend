package com.petrotal.ahcbackend.service.file.impl;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.file.DataService;
import com.petrotal.ahcbackend.service.file.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final DataService dataService;
    private final DataAccessService dataAccessService;

    @Override
    public void processingFile(MultipartFile file) {
        List<Data> data = new ArrayList<>();

        data.addAll(dataService.manageData(file, "Diesel Operaciones"));
        data.addAll(dataService.manageData(file, "Gasolina Operaciones"));

        dataAccessService.saveAll(data);
    }
}
