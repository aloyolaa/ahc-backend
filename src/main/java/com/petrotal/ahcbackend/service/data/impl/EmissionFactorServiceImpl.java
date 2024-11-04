package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.entity.EmissionFactor;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.GasMapper;
import com.petrotal.ahcbackend.repository.EmissionFactorRepository;
import com.petrotal.ahcbackend.service.data.EmissionFactorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmissionFactorServiceImpl implements EmissionFactorService {
    private final EmissionFactorRepository emissionFactorRepository;
    private final GasMapper gasMapper;

    @Override
    @Transactional(readOnly = true)
    public GasDto findByYear(Integer year, String fuelType, String consumptionType) {
        try {
            return gasMapper.toGasDto(
                    emissionFactorRepository.findByYear(year, fuelType.toUpperCase(), consumptionType.toUpperCase())
                            .orElseThrow(() -> new EntityNotFoundException("No existen datos sobre Factores de Emisión para el año " + year + " " + fuelType + " " + consumptionType))
            );
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public Optional<EmissionFactor> findByYearOptional(Integer year, String fuelType, String consumptionType) {
        try {
            return emissionFactorRepository.findByYear(year, fuelType.toUpperCase(), consumptionType.toUpperCase());
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional
    public void save(GasDto gasDto, String fuelType, String consumptionType) {
        try {
            Optional<EmissionFactor> byYearOptional = findByYearOptional(Year.now().getValue(), fuelType, consumptionType);
            if (byYearOptional.isPresent()) {
                EmissionFactor byYear = byYearOptional.orElseThrow();
                emissionFactorRepository.save(gasMapper.partialUpdate(byYear, gasDto));
            } else {
                EmissionFactor emissionFactor = gasMapper.toEmissionFactor(gasDto);
                emissionFactor.setFuelType(fuelType.toUpperCase());
                emissionFactor.setConsumptionType(consumptionType.toUpperCase());
                emissionFactorRepository.save(emissionFactor);
            }
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.");
        }
    }
}
