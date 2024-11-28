package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.dto.SignatoryDto;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import com.petrotal.ahcbackend.service.security.UserService;
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
    private final UserService userService;
    private final DataAccessService dataAccessService;
    private final AccessHistoryService accessHistoryService;

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> save(@RequestParam MultipartFile signatureFile) {
        signatoryService.save(signatureFile);
        accessHistoryService.logAccessHistory(null, "Guardado su Firma");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Firma registrada correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/by-user")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getByUser() {
        SignatoryDto byUser = signatoryService.getByUser(userService.getUsernameFromSecurityContext());
        accessHistoryService.logAccessHistory(null, "Consultado su firma");
        return new ResponseEntity<>(
                new ResponseDto(
                        byUser,
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/sign/{voucherId}")
    @PreAuthorize("hasAnyAuthority('FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> sign(@PathVariable Long voucherId) {
        dataAccessService.sign(voucherId);
        accessHistoryService.logAccessHistory(null, "Firmado el Vale con el ID: " + voucherId);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Voucher firmado correctamente",
                        true)
                , HttpStatus.OK);
    }
}
