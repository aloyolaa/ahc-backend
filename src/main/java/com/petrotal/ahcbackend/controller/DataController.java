package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.mapper.DataMapper;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.report.ReportGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataAccessService dataAccessService;
    private final DataMapper dataMapper;
    private final ReportGenerator reportGenerator;

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody DataDto dataDto) {
        dataAccessService.save(dataDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/voucher-number/{voucherNumber}")
    @PreAuthorize("hasAnyAuthority('REGISTER', 'FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getByVoucherNumber(@PathVariable String voucherNumber) {
        return new ResponseEntity<>(
                new ResponseDto(
                        dataMapper.toDataDto(dataAccessService.findByVoucherNumber(voucherNumber)),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/next-voucher")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getNextVoucherNumber() {
        return new ResponseEntity<>(
                new ResponseDto(
                        dataAccessService.getNextVoucherNumber(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/pending-vouchers")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getPendingVouchers() {
        return new ResponseEntity<>(
                new ResponseDto(
                        dataAccessService.findBySignatory(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/cancel/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> cancelVoucher(@PathVariable Long id) {
        dataAccessService.cancelVoucher(id);
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
        return new ResponseEntity<>(
                new ResponseDto(
                        dataAccessService.findBySignatory().size(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/report/{voucherNumber}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getReport(@PathVariable String voucherNumber) {
        return new ResponseEntity<>(
                new ResponseDto(
                        reportGenerator.generateReport(voucherNumber),
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/filter/{areaId}/{contractorId}/{startDate}/{endDate}/{status}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getByFilter(@PathVariable Long areaId, @PathVariable Long contractorId, @PathVariable LocalDate startDate, @PathVariable LocalDate endDate, @PathVariable String status) {
        return new ResponseEntity<>(
                new ResponseDto(
                        dataAccessService.findByFilter(areaId, contractorId, startDate, endDate, status),
                        true)
                , HttpStatus.OK
        );
    }
}
