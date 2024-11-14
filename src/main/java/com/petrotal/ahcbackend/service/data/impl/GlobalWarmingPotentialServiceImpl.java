package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.GasDto;
import com.petrotal.ahcbackend.entity.GlobalWarmingPotential;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.GasMapper;
import com.petrotal.ahcbackend.repository.GlobalWarmingPotentialRepository;
import com.petrotal.ahcbackend.service.data.GlobalWarmingPotentialService;
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
public class GlobalWarmingPotentialServiceImpl implements GlobalWarmingPotentialService {
    private final GlobalWarmingPotentialRepository globalWarmingPotentialRepository;
    private final GasMapper gasMapper;

    @Override
    @Transactional(readOnly = true)
    public GasDto findByYear(Integer year) {
        try {
            return gasMapper.toGasDto(
                    globalWarmingPotentialRepository.findByYear(year)
                            .orElseThrow(() -> new EntityNotFoundException("No existen datos sobre Potenciales de Calentamiento Global"))
            );
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public Optional<GlobalWarmingPotential> findByYearOptional(Integer year) {
        try {
            return globalWarmingPotentialRepository.findByYear(year);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional
    public void save(GasDto gasDto) {
        try {
            Optional<GlobalWarmingPotential> byYearOptional = findByYearOptional(Year.now().getValue());
            if (byYearOptional.isPresent()) {
                GlobalWarmingPotential byYear = byYearOptional.orElseThrow();
                globalWarmingPotentialRepository.save(gasMapper.partialUpdate(byYear, gasDto));
            } else {
                GlobalWarmingPotential globalWarmingPotential = gasMapper.toGlobalWarmingPotential(gasDto);
                globalWarmingPotentialRepository.save(globalWarmingPotential);
            }
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.");
        }
    }
}
