package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;
import com.petrotal.ahcbackend.dto.CarbonFootprintVariablesDto;
import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.CarbonFootprintService;
import com.petrotal.ahcbackend.service.data.EmissionFactorService;
import com.petrotal.ahcbackend.service.data.GlobalWarmingPotentialService;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Year;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carbon-footprint")
public class CarbonFootprintController {
    private final EmissionFactorService emissionFactorService;
    private final GlobalWarmingPotentialService globalWarmingPotentialService;
    private final CarbonFootprintService carbonFootprintService;
    private final AccessHistoryService accessHistoryService;

    @GetMapping("/emission-factor/{fuelType}/{consumptionType}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getEmissionFactor(@PathVariable String fuelType, @PathVariable String consumptionType) {
        GasDto byYear = emissionFactorService.findByYear(Year.now().getValue(), fuelType, consumptionType);
        accessHistoryService.logAccessHistory(null, "Consultado los Datos de Factores de Emisión para Combustible " + fuelType + " y Maquinaria " + consumptionType);
        return new ResponseEntity<>(
                new ResponseDto(
                        byYear,
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/global-warming-potential")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getGlobalWarmingPotential() {
        GasDto byYear = globalWarmingPotentialService.findByYear(Year.now().getValue());
        accessHistoryService.logAccessHistory(null, "Consultado los Datos de Potenciales de Calentamiento Global");
        return new ResponseEntity<>(
                new ResponseDto(
                        byYear,
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/emission-factor/save/{fuelType}/{consumptionType}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> saveEmissionFactor(@Valid @RequestBody GasDto gasDto, @PathVariable String fuelType, @PathVariable String consumptionType) {
        emissionFactorService.save(gasDto, fuelType, consumptionType);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de Factores de Emisión para Combustible " + fuelType + " y Maquinaria " + consumptionType);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos de Factor de Emisión guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/global-warming-potential/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> saveGlobalWarmingPotentialDto(@Valid @RequestBody GasDto gasDto) {
        globalWarmingPotentialService.save(gasDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de Potenciales de Calentamiento Global");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos de Potencial de Calentamiento Global guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/calculate")
    public ResponseEntity<ResponseDto> getCarbonFootprint() {
        CarbonFootprintDto carbonFootprint = carbonFootprintService.getCarbonFootprint();
        accessHistoryService.logAccessHistory(null, "Consultado el Cálculo de Huella de Carbono");
        return new ResponseEntity<>(
                new ResponseDto(
                        carbonFootprint
                        , true)
                , HttpStatus.OK);
    }

    @GetMapping("/variables")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAllVariables() {
        CarbonFootprintVariablesDto allVariables = carbonFootprintService.getAllVariables();
        accessHistoryService.logAccessHistory(null, "Consultado todos loa Datos de Factores de Emisión y Potenciales de Calentamiento Global");
        return new ResponseEntity<>(
                new ResponseDto(
                        allVariables
                        , true)
                , HttpStatus.OK);
    }
}
