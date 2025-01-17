package com.petrotal.ahcbackend.service.file.impl;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.data.EquipmentService;
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
    private final AreaService areaService;
    private final ContractorService contractorService;
    private final EquipmentService equipmentService;

    @Override
    public void processingFile(MultipartFile file) {
        List<Data> data = new ArrayList<>();

        List<AreaDto> areas = areaService.findAll();
        List<ContractorDto> contractors = contractorService.findAll();
        List<EquipmentDto> equipments = equipmentService.findAll();

        data.addAll(dataService.manageData(file, "Diesel Operaciones", areas, contractors, equipments));
        data.addAll(dataService.manageData(file, "Gasolina Operaciones", areas, contractors, equipments));

        dataAccessService.saveAll(data);
    }
}
