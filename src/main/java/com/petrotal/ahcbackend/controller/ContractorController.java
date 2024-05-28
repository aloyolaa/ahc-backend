package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.ContractorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
