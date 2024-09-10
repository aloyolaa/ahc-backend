package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.ContractorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contractor")
@RequiredArgsConstructor
public class ContractorController {
    private final ContractorService contractorService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        return new ResponseEntity<>(
                new ResponseDto(
                        contractorService.findAll(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseDto(
                        contractorService.findById(id),
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody ContractorDto contractorDto) {
        return new ResponseEntity<>(
                new ResponseDto(
                        contractorService.save(contractorDto),
                        true)
                , HttpStatus.OK
        );
    }
}
