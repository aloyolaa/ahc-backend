package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.CarbonFootprintDto;
import com.petrotal.ahcbackend.dto.CarbonFootprintVariablesDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.EmissionFactor;
import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;
import com.petrotal.ahcbackend.enumerator.EquipmentType;
import com.petrotal.ahcbackend.enumerator.FuelType;
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

import static com.petrotal.ahcbackend.service.util.NumberFormat.formatNumber;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarbonFootprintServiceImpl implements CarbonFootprintService {
    private final DataAccessService dataAccessService;
    private final ConsumptionServiceImpl consumptionService;
    private final EmissionFactorService emissionFactorService;
    private final GlobalWarmingPotentialService globalWarmingPotentialService;

    @Override
    public CarbonFootprintDto getCarbonFootprint() {
        List<Data> data = dataAccessService.findByYear(Year.now().getValue());

        Double stationaryMachineryDieselConsumption = consumptionService.calculateConsumption(FuelType.DIESEL, EquipmentType.STATIONARY, data);
        Double stationaryMachineryGasolineConsumption = consumptionService.calculateConsumption(FuelType.GASOLINE, EquipmentType.STATIONARY, data);
        Double mobileMachineryDieselConsumption = consumptionService.calculateConsumption(FuelType.DIESEL, EquipmentType.MOBILE, data);
        Double mobileMachineryGasolineConsumption = consumptionService.calculateConsumption(FuelType.GASOLINE, EquipmentType.MOBILE, data);

        Double stationaryMachineryDieselCalculate = calculateCarbonFootprint(FuelType.DIESEL, EquipmentType.STATIONARY, stationaryMachineryDieselConsumption);
        Double stationaryMachineryGasolineCalculate = calculateCarbonFootprint(FuelType.GASOLINE, EquipmentType.STATIONARY, stationaryMachineryGasolineConsumption);
        Double mobileMachineryDieselCalculate = calculateCarbonFootprint(FuelType.DIESEL, EquipmentType.MOBILE, mobileMachineryDieselConsumption);
        Double mobileMachineryGasolineCalculate = calculateCarbonFootprint(FuelType.GASOLINE, EquipmentType.MOBILE, mobileMachineryGasolineConsumption);

        return new CarbonFootprintDto(
                stationaryMachineryDieselConsumption,
                formatNumber(stationaryMachineryDieselConsumption),
                stationaryMachineryDieselCalculate,
                formatNumber(stationaryMachineryDieselCalculate),
                stationaryMachineryGasolineConsumption,
                formatNumber(stationaryMachineryGasolineConsumption),
                stationaryMachineryGasolineCalculate,
                formatNumber(stationaryMachineryGasolineCalculate),
                mobileMachineryDieselConsumption,
                formatNumber(mobileMachineryDieselConsumption),
                mobileMachineryDieselCalculate,
                formatNumber(mobileMachineryDieselCalculate),
                mobileMachineryGasolineConsumption,
                formatNumber(mobileMachineryGasolineConsumption),
                mobileMachineryGasolineCalculate,
                formatNumber(mobileMachineryGasolineCalculate)
        );
    }

    @Override
    public CarbonFootprintVariablesDto getAllVariables() {
        return new CarbonFootprintVariablesDto(
                emissionFactorService.findByYear(Year.now().getValue(), FuelType.GASOLINE.name(), EquipmentType.STATIONARY.name()),
                emissionFactorService.findByYear(Year.now().getValue(), FuelType.DIESEL.name(), EquipmentType.STATIONARY.name()),
                emissionFactorService.findByYear(Year.now().getValue(), FuelType.GASOLINE.name(), EquipmentType.MOBILE.name()),
                emissionFactorService.findByYear(Year.now().getValue(), FuelType.DIESEL.name(), EquipmentType.MOBILE.name()),
                globalWarmingPotentialService.findByYear(Year.now().getValue())
        );
    }

    @Override
    public Double calculateCarbonFootprint(FuelType fuelType, EquipmentType equipmentType, Double consumption) {
        Integer year = Year.now().getValue();

        Optional<EmissionFactor> emissionFactorByYearOptional = emissionFactorService.findByYearOptional(year, fuelType.name(), equipmentType.name());
        Optional<GlobalWarmingPotential> globalWarmingPotentialByYearOptional = globalWarmingPotentialService.findByYearOptional(year);

        if (emissionFactorByYearOptional.isPresent() && globalWarmingPotentialByYearOptional.isPresent()) {
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
