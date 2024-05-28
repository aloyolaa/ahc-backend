package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.EmissionFactorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emission-factor")
public class EmissionFactorController {
    private final EmissionFactorService emissionFactorService;

    @GetMapping("/{fuelType}/{consumptionType}")
    public ResponseEntity<ResponseDto> getEmissionFactor(@PathVariable String fuelType, @PathVariable String consumptionType) {
        return new ResponseEntity<>(
                new ResponseDto(
                        emissionFactorService.findByYear(Year.now().getValue(), fuelType.toLowerCase(), consumptionType.toLowerCase()),
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/save/{fuelType}/{consumptionType}")
    public ResponseEntity<ResponseDto> saveEmissionFactor(@Valid @RequestBody GasDto gasDto, @PathVariable String fuelType, @PathVariable String consumptionType) {
        emissionFactorService.save(gasDto, fuelType.toLowerCase(), consumptionType.toLowerCase());
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos de Factor de Emisión guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }
}
