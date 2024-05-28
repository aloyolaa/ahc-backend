package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.GlobalWarmingPotentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global-warming-potential")
public class GlobalWarmingPotentialController {
    private final GlobalWarmingPotentialService globalWarmingPotentialService;

    @GetMapping("/global-warming-potential")
    public ResponseEntity<ResponseDto> getGlobalWarmingPotential() {
        return new ResponseEntity<>(
                new ResponseDto(
                        globalWarmingPotentialService.findByYear(Year.now().getValue()),
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/global-warming-potential/save")
    public ResponseEntity<ResponseDto> saveGlobalWarmingPotentialDto(@Valid @RequestBody GasDto gasDto) {
        globalWarmingPotentialService.save(gasDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos de Potencial de Calentamiento Global guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }
}
