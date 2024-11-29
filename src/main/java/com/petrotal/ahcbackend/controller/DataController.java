package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.DataListDto;
import com.petrotal.ahcbackend.dto.DataViewDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.mapper.DataMapper;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.report.ReportGenerator;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataAccessService dataAccessService;
    private final DataMapper dataMapper;
    private final ReportGenerator reportGenerator;
    private final AccessHistoryService accessHistoryService;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody DataDto dataDto) {
        dataAccessService.save(dataDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de un Vale");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/voucher-number/{voucherNumber}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getByVoucherNumber(@PathVariable String voucherNumber) {
        DataDto dataDto = dataMapper.toDataDto(dataAccessService.findByVoucherNumber(voucherNumber));
        accessHistoryService.logAccessHistory(null, "Consultado los Datos del Vale con el Número: " + voucherNumber);
        return new ResponseEntity<>(
                new ResponseDto(
                        dataDto,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/detail/{voucherNumber}")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getDetail(@PathVariable String voucherNumber) {
        DataViewDto dataViewDto = dataMapper.toDataViewDto(dataAccessService.findByVoucherNumber(voucherNumber));
        accessHistoryService.logAccessHistory(null, "Consultado el Detalle del Vale con el Número: " + voucherNumber);
        return new ResponseEntity<>(
                new ResponseDto(
                        dataViewDto,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/next-voucher")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getNextVoucherNumber() {
        int nextVoucherNumber = dataAccessService.getNextVoucherNumber();
        accessHistoryService.logAccessHistory(null, "Consultado el próximo Número de Vale");
        return new ResponseEntity<>(
                new ResponseDto(
                        nextVoucherNumber,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/pending-vouchers")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getPendingVouchers() {
        List<DataListDto> bySignatory = dataAccessService.findBySignatory();
        accessHistoryService.logAccessHistory(null, "Consultado los Vales que tiene pendiente la firma de su cargo");
        return new ResponseEntity<>(
                new ResponseDto(
                        bySignatory,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> cancelVoucher(@PathVariable Long id) {
        dataAccessService.cancelVoucher(id);
        accessHistoryService.logAccessHistory(null, "Cancelado el Vale con el ID: " + id);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Voucher anulado.",
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/has-pending-vouchers")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> hasPendingVouchers() {
        int size = dataAccessService.findBySignatory().size();
        accessHistoryService.logAccessHistory(null, "Consultado la cantidad de Vales que tiene pendiente la firma de su cargo");
        return new ResponseEntity<>(
                new ResponseDto(
                        size,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/report/{voucherNumber}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getReport(@PathVariable String voucherNumber) {
        String report = reportGenerator.generateReport(voucherNumber);
        accessHistoryService.logAccessHistory(null, "Generado el PDF del Vale con el Número: " + voucherNumber);
        return new ResponseEntity<>(
                new ResponseDto(
                        report,
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/filter/{areaId}/{contractorId}/{startDate}/{endDate}/{status}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getByFilter(@PathVariable Long areaId, @PathVariable Long contractorId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable String status) {
        List<DataListDto> byFilter = dataAccessService.findByFilter(areaId, contractorId, startDate, endDate, status);
        accessHistoryService.logAccessHistory(null, "Consultado los Vales según el Area con el ID: " + areaId + ", con Contratista con el ID: " + contractorId + ", el Estado " + status + " y entre las Fechas desde el " + startDate + " al " + endDate);
        return new ResponseEntity<>(
                new ResponseDto(
                        byFilter,
                        true)
                , HttpStatus.OK
        );
    }
}
