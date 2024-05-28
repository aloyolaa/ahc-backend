package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.EmissionFactor;
import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;
import com.petrotal.ahcbackend.service.data.CarbonFootprintService;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.data.EmissionFactorService;
import com.petrotal.ahcbackend.service.data.GlobalWarmingPotentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarbonFootprintServiceImpl implements CarbonFootprintService {
    private final DataAccessService dataAccessService;
    private final EmissionFactorService emissionFactorService;
    private final GlobalWarmingPotentialService globalWarmingPotentialService;

    @Override
    public CarbonFootprintDto getCarbonFootprint() {
        return new CarbonFootprintDto(
                calculateCarbonFootprint("DIESEL", "STATIONARY-MACHINERY"),
                calculateCarbonFootprint("GASOLINE", "STATIONARY-MACHINERY"),
                calculateCarbonFootprint("DIESEL", "MOBILE-MACHINERY")
        );
    }

    @Override
    public Double calculateCarbonFootprint(String fuelType, String consumptionType) {
        Integer year = Year.now().getValue();

        Optional<EmissionFactor> emissionFactorByYearOptional = emissionFactorService.findByYearOptional(year, fuelType, consumptionType);
        Optional<GlobalWarmingPotential> globalWarmingPotentialByYearOptional = globalWarmingPotentialService.findByYearOptional(year);

        if (emissionFactorByYearOptional.isPresent() && globalWarmingPotentialByYearOptional.isPresent()) {
            double consumption = dataAccessService.findByYear(Year.now().getValue())
                    .stream()
                    .filter(d -> d.getEquipment().getType().equals(consumptionType) && d.getDescription().equals(fuelType))
                    .mapToDouble(Data::getConsumption)
                    .sum();

            EmissionFactor emissionFactor = emissionFactorByYearOptional.orElseThrow();
            GlobalWarmingPotential globalWarmingPotential = globalWarmingPotentialByYearOptional.orElseThrow();

            double co2EmissionFactor = emissionFactor.getCo2();
            double ch4EmissionFactor = emissionFactor.getCh4();
            double n2oEmissionFactor = emissionFactor.getN2o();

            double co2GlobalWarmingPotential = globalWarmingPotential.getCo2();
            double ch4GlobalWarmingPotential = globalWarmingPotential.getCh4();
            double n2oGlobalWarmingPotential = globalWarmingPotential.getN2o();

            double co2Emission = consumption * co2EmissionFactor;
            double ch4Emission = consumption * ch4EmissionFactor;
            double n2oEmission = consumption * n2oEmissionFactor;

            return ((co2Emission * co2GlobalWarmingPotential) + (ch4Emission * ch4GlobalWarmingPotential) + (n2oEmission * n2oGlobalWarmingPotential)) * 0.001;
        }

        return 0.0;
    }
}
