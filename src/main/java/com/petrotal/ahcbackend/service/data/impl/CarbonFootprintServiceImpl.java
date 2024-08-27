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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarbonFootprintServiceImpl implements CarbonFootprintService {
    private final DataAccessService dataAccessService;
    private final EmissionFactorService emissionFactorService;
    private final GlobalWarmingPotentialService globalWarmingPotentialService;

    @Override
    public CarbonFootprintDto getCarbonFootprint() {
        List<Data> data = dataAccessService.findByYear(Year.now().getValue());
        log.info("data {}", data.size());
        return new CarbonFootprintDto(
                calculateCarbonFootprint("DIESEL", "STATIONARY-MACHINERY", data),
                calculateCarbonFootprint("GASOLINE", "STATIONARY-MACHINERY", data),
                calculateCarbonFootprint("DIESEL", "MOBILE-MACHINERY", data),
                calculateCarbonFootprint("GASOLINE", "MOBILE-MACHINERY", data)
        );
    }

    @Override
    public Double calculateCarbonFootprint(String fuelType, String consumptionType, List<Data> data) {
        Integer year = Year.now().getValue();

        Optional<EmissionFactor> emissionFactorByYearOptional = emissionFactorService.findByYearOptional(year, fuelType, consumptionType);
        Optional<GlobalWarmingPotential> globalWarmingPotentialByYearOptional = globalWarmingPotentialService.findByYearOptional(year);

        if (emissionFactorByYearOptional.isPresent() && globalWarmingPotentialByYearOptional.isPresent()) {
            double consumption = data
                    .stream()
                    .filter(d -> d.getEquipment().getType() != null && d.getEquipment().getType().equals(consumptionType) && d.getDescription().equals(fuelType))
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
