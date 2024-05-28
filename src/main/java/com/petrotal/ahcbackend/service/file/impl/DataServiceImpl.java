package com.petrotal.ahcbackend.service.file.impl;

import com.petrotal.ahcbackend.entity.*;
import com.petrotal.ahcbackend.exception.FileProcessingException;
import com.petrotal.ahcbackend.exception.InvalidFileFormatException;
import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.service.data.DataAccessService;
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
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataServiceImpl implements DataService {
    private final AreaService areaService;
    private final ContractorService contractorService;
    private final EquipmentService equipmentService;
    private final DataAccessService dataAccessService;
    private static final String DIESEL_SHEET = "Diesel Operaciones";

    public void manageData(MultipartFile file, String sheetName) {
        Workbook workbook = openWorkbook(file);
        Sheet sheet = validateSheet(workbook, sheetName);

        List<Data> data = new ArrayList<>();

        //System.out.println(sheet.getSheetName());
        int lastRowNum = sheet.getLastRowNum();
        //System.out.println(lastRowNum);

        for (int i = getInitialRow(sheetName); i <= getTotalRows(sheet) + 1; i++) {
            System.out.println("Row: " + (i + 1));
            Data d = new Data();

            LocalDate dispatchDate = getDispatchDate(sheet, i);

            if (dispatchDate.getYear() > 2020) {
                Double consumption = getConsumption(sheet, i);
                Equipment equipment = getBase(sheet, i, 3, equipmentService::findByName);

                if (consumption != null && consumption > 0 && equipment != null) {
                    d.setVoucherNumber(getVoucherNumber(sheet, i));
                    d.setDispatchDate(dispatchDate);
                    d.setDescription(getDescription(sheetName));
                    d.setConsumption(consumption);
                    d.setArea(getBase(sheet, i, 1, areaService::findByName));
                    d.setContractor(getBase(sheet, i, 2, contractorService::findByName));
                    d.setEquipment(equipment);
                    data.add(d);
                    System.out.println(d);
                }
            }
        }

        dataAccessService.saveAll(data);
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
        System.out.println("extract vou");

        DataFormatter formatter = new DataFormatter();

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            //return cell.toString();
            return formatter.formatCellValue(cell);
        }

        return null;
    }

    @Override
    public LocalDate getDispatchDate(Sheet sheet, int rowIndex) {
        int column = sheet.getSheetName().equals(DIESEL_SHEET) ? 2 : 0;
        Cell cell = getCell(sheet, rowIndex, column);
        System.out.println("ext fecha");
        System.out.println("t fecha");

        if (cell != null && cell.getCellType() != CellType.BLANK) {
            DataFormatter formatter = new DataFormatter();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yy");

            String cellStringValue = formatter.formatCellValue(cell);
            System.out.println(cellStringValue);

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
                //log.error("Valor no numérico encontrado en la fila {}, columna {}: {}", rowIndex, column, cellValue, e);
                return null;
            }
        }

        return null;
    }

    @Override
    public String getDescription(String sheetName) {
        return sheetName.equals(DIESEL_SHEET) ? "DIESEL" : "GASOLINE";
    }

    @Override
    public <T extends Base> T getBase(Sheet sheet, int rowIndex, int base, Function<String, T> findByName) {
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
            name = formatter.formatCellValue(cell).trim();
            return findByName.apply(name);
        }

        return null;
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
