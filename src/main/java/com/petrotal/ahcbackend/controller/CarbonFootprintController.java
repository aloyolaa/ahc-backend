package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.CarbonFootprintService;
import com.petrotal.ahcbackend.service.data.EmissionFactorService;
import com.petrotal.ahcbackend.service.data.GlobalWarmingPotentialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon-footprint")
public class CarbonFootprintController {
    private final EmissionFactorService emissionFactorService;
    private final GlobalWarmingPotentialService globalWarmingPotentialService;
    private final CarbonFootprintService carbonFootprintService;

    @GetMapping("/emission-factor/{fuelType}/{consumptionType}")
    public ResponseEntity<ResponseDto> getEmissionFactor(@PathVariable String fuelType, @PathVariable String consumptionType) {
        return new ResponseEntity<>(
                new ResponseDto(
                        emissionFactorService.findByYear(Year.now().getValue(), fuelType, consumptionType),
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/global-warming-potential")
    public ResponseEntity<ResponseDto> getGlobalWarmingPotential() {
        return new ResponseEntity<>(
                new ResponseDto(
                        globalWarmingPotentialService.findByYear(Year.now().getValue()),
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/emission-factor/save/{fuelType}/{consumptionType}")
    public ResponseEntity<ResponseDto> saveEmissionFactor(@Valid @RequestBody GasDto gasDto, @PathVariable String fuelType, @PathVariable String consumptionType) {
        emissionFactorService.save(gasDto, fuelType, consumptionType);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos de Factor de Emisión guardados correctamente.",
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

    @GetMapping("/calculate")
    public ResponseEntity<ResponseDto> getCarbonFootprint() {
        return new ResponseEntity<>(
                new ResponseDto(
                        carbonFootprintService.getCarbonFootprint()
                        , true)
                , HttpStatus.OK);
    }
}
