package com.petrotal.ahcbackend.service.file;

import com.petrotal.ahcbackend.entity.Base;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.function.Function;

public interface DataService {
    void manageData(MultipartFile file, String sheetName);

    String getVoucherNumber(Sheet sheet, int rowIndex);

    LocalDate getDispatchDate(Sheet sheet, int rowIndex);

    Double getConsumption(Sheet sheet, int rowIndex);

    <T extends Base> T getBase(Sheet sheet, int rowIndex, int base, Function<String, T> findByName);

    String getDescription(String sheetName);
}
