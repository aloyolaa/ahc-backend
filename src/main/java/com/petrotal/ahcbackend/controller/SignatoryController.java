package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.DataSignatoryService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/signatory")
@RequiredArgsConstructor
public class SignatoryController {
    private final SignatoryService signatoryService;
    private final DataSignatoryService dataSignatoryService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> save(@RequestParam MultipartFile signatureFile) {
        signatoryService.save(signatureFile);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Firma registrada correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/by-user")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getByUser() {
        return new ResponseEntity<>(
                new ResponseDto(
                        signatoryService.getByUser(),
                        true)
                , HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> update(@RequestParam MultipartFile signatureFile) {
        signatoryService.update(signatureFile);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Firma registrada correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/sign/{voucherId}")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> sign(@PathVariable Long voucherId) {
        dataSignatoryService.sign(voucherId);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Voucher firmado correctamente",
                        true)
                , HttpStatus.OK);
    }
}
