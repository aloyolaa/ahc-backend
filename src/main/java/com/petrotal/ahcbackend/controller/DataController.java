package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final DataAccessService dataAccessService;

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody DataDto dataDto) {
        dataAccessService.save(dataDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
