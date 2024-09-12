package com.petrotal.ahcbackend.service.file.impl;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.*;
import com.petrotal.ahcbackend.enumerator.FuelType;
import com.petrotal.ahcbackend.exception.FileProcessingException;
import com.petrotal.ahcbackend.exception.InvalidFileFormatException;
import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.service.data.EquipmentService;
import com.petrotal.ahcbackend.service.file.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final AreaService areaService;
    private final ContractorService contractorService;
    private final EquipmentService equipmentService;
    private static final String DIESEL_SHEET = "Diesel Operaciones";

    public List<Data> manageData(MultipartFile file, String sheetName, List<AreaDto> areaDtos, List<ContractorDto> contractorDtos, List<EquipmentDto> equipmentDtos) {
        Workbook workbook = openWorkbook(file);
        Sheet sheet = validateSheet(workbook, sheetName);

        List<Data> data = new ArrayList<>();

        int lastRowNum = sheet.getLastRowNum();
        log.info("Total de Filas: {}", lastRowNum);

        for (int i = getInitialRow(sheetName); i <= getTotalRows(sheet) + 1; i++) {
            log.info("Fila: {}", i + 1);
            Data d = new Data();
            LocalDate dispatchDate = getDispatchDate(sheet, i);

            if (dispatchDate.getYear() > 2020) {
                Double consumption = getConsumption(sheet, i);

                if (consumption != null && consumption > 0) {
                    Equipment equipment = getObject(sheet, i, 3, equipmentDtos);

                    if (equipment != null) {
                        d.setVoucherNumber(getVoucherNumber(sheet, i));
                        d.setDispatchDate(dispatchDate);
                        d.setArea(getObject(sheet, i, 1, areaDtos));
                        d.setContractor(getObject(sheet, i,2, contractorDtos));
                        d.setEquipment(equipment);

                        DataDetail dataDetail = new DataDetail();
                        dataDetail.setOrderedQuantity(consumption);
                        dataDetail.setDescription(getDescription(sheetName));
                        dataDetail.setQuantityShipped(consumption);
                        dataDetail.setUnitOfMeasurement("GALONES");
                        dataDetail.setFinalStock(getStock(sheet, i));
                        dataDetail.setData(d);

                        d.getDataDetails().add(dataDetail);

                        data.add(d);
                        log.info("Data: {}", d);
                    }
                }
            }
        }

        return data;
    }

    private int getInitialRow(String sheetName) {
        return sheetName.equals(DIESEL_SHEET) ? 4 : 1;
    }

    private int getTotalRows(Sheet sheet) {
        return sheet.getSheetName().equals(DIESEL_SHEET) ? sheet.getLastRowNum() - 2 : sheet.getLastRowNum() - 1;
    }

    @Override
    public String getVoucherNumber(Sheet sheet, int rowIndex) {
        int column = sheet.getSheetName().equals(DIESEL_SHEET) ? 19 : 12;
        Cell cell = getCell(sheet, rowIndex, column);

        DataFormatter formatter = new DataFormatter();

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            return formatter.formatCellValue(cell);
        }

        return null;
    }

    @Override
    public LocalDate getDispatchDate(Sheet sheet, int rowIndex) {
        int column = sheet.getSheetName().equals(DIESEL_SHEET) ? 2 : 0;
        Cell cell = getCell(sheet, rowIndex, column);

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            DataFormatter formatter = new DataFormatter();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy");

            String cellStringValue = formatter.formatCellValue(cell);

            if (cell.getCellType() == CellType.FORMULA) {
                log.info(cellStringValue);
                String cellReference = cellStringValue.substring(1);
                int row = Integer.parseInt(cellReference.replaceAll("\\D", "")); //[^0-9]
                log.info("Fila Referenciada: {}", row);

                return getDispatchDate(sheet, row - 1);
            }

            if (!cellStringValue.isEmpty()) {
                try {
                    return LocalDate.parse(cellStringValue, dtf);
                } catch (DateTimeParseException e) {
                    log.error("Error al parsear la fecha: {}", cell.getNumericCellValue(), e);
                    return null;
                }
            }
        }

        return null;
    }

    @Override
    public Double getConsumption(Sheet sheet, int rowIndex) {
        int column = sheet.getSheetName().equals(DIESEL_SHEET) ? 17 : 10;
        Cell cell = getCell(sheet, rowIndex, column);

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            String cellValue = cell.toString().trim();

            try {
                return Double.parseDouble(cellValue);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    @Override
    public Double getStock(Sheet sheet, int rowIndex) {
        int column = sheet.getSheetName().equals(DIESEL_SHEET) ? 18 : 11;
        Cell cell = getCell(sheet, rowIndex, column);

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            String cellValue = cell.toString().trim();

            try {
                return Double.parseDouble(cellValue);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        return null;
    }

    @Override
    public <T extends Base, E> T getObject(Sheet sheet, int rowIndex, int base, List<E> entityDtos) {
        int column;

        switch (base) {
            case 1 -> column = sheet.getSheetName().equals(DIESEL_SHEET) ? 6 : 1;
            case 2 -> column = sheet.getSheetName().equals(DIESEL_SHEET) ? 10 : 2;
            case 3 -> column = sheet.getSheetName().equals(DIESEL_SHEET) ? 11 : 5;
            default -> throw new IllegalStateException("Unexpected value: " + base);
        }

        Cell cell = getCell(sheet, rowIndex, column);
        DataFormatter formatter = new DataFormatter();
        String name;

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            name = formatter.formatCellValue(cell).trim().toUpperCase();

            if (!name.contains("COMUNIDAD")) {

                switch (base) {
                    case 1 -> {
                        return (T) areaService.findByName(name, (List<AreaDto>) entityDtos);
                    }
                    case 2 -> {
                        return (T) contractorService.findByName(name, (List<ContractorDto>) entityDtos);
                    }
                    case 3 -> {
                        return (T) equipmentService.findByName(name, (List<EquipmentDto>) entityDtos);
                    }
                    default -> {
                        return null;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public FuelType getDescription(String sheetName) {
        return sheetName.equals(DIESEL_SHEET) ? FuelType.DIESEL : FuelType.GASOLINE;
    }

    private Workbook openWorkbook(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            return WorkbookFactory.create(inputStream);
        } catch (IOException e) {
            throw new FileProcessingException("Error al abrir el archivo.", e);
        }
    }

    private Sheet validateSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            throw new InvalidFileFormatException("El archivo subido no cumple con el formato establecido.");
        }

        return sheet;
    }

    private Cell getCell(Sheet sheetName, int rowIndex, int columnIndex) {
        Row row = sheetName.getRow(rowIndex);

        if (row != null) {
            return row.getCell(columnIndex);
        }

        return null;
    }
}
