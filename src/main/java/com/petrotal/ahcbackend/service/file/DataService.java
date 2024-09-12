package com.petrotal.ahcbackend.service.file;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.*;
import com.petrotal.ahcbackend.enumerator.FuelType;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface DataService {
    List<Data> manageData(MultipartFile file, String sheetName, List<AreaDto> areaDtos, List<ContractorDto> contractorDtos, List<EquipmentDto> equipmentDtos);

    String getVoucherNumber(Sheet sheet, int rowIndex);

    LocalDate getDispatchDate(Sheet sheet, int rowIndex);

    Double getConsumption(Sheet sheet, int rowIndex);

    Double getStock(Sheet sheet, int rowIndex);

    <T extends Base, E> T getObject(Sheet sheet, int rowIndex, int base, List<E> entityDtos);

    FuelType getDescription(String sheetName);
}
