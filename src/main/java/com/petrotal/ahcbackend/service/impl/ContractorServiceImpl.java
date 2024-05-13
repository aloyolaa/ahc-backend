package com.petrotal.ahcbackend.service.impl;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.entity.Contractor;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.ContractorMapper;
import com.petrotal.ahcbackend.repository.ContractorRepository;
import com.petrotal.ahcbackend.service.ContractorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {
    private final ContractorRepository contractorRepository;
    private final ContractorMapper contractorMapper;

    @Override
    @Transactional(readOnly = true)
    public Contractor findById(Long id) {
        try {
            return contractorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Contratista con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractorDto> findAll() {
        try {
            return contractorMapper.toContractorDtos(contractorRepository.findAll());
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }
}
