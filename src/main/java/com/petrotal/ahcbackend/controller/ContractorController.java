package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {
    private final ContractorService contractorService;
    private final AccessHistoryService accessHistoryService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<ContractorDto> all = contractorService.findAll();
        accessHistoryService.logAccessHistory(null, "Consultado la Lista de Contratistas");
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        ContractorDto byId = contractorService.findById(id);
        accessHistoryService.logAccessHistory(null, "Consultado los Datos del Contratista con el ID: " + id);
        return new ResponseEntity<>(
                new ResponseDto(
                        byId,
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody ContractorDto contractorDto) {
        contractorService.save(contractorDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de un Contratista");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
