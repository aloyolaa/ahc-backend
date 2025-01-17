package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.entity.Contractor;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.ContractorMapper;
import com.petrotal.ahcbackend.repository.ContractorRepository;
import com.petrotal.ahcbackend.service.data.ContractorService;
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
    public ContractorDto findById(Long id) {
        try {
            Contractor contractor = contractorRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Contratista con el ID " + id + " no existe."));
            return contractorMapper.toContractorDto(contractor);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public Contractor findByName(String name, List<ContractorDto> contractorDtos) {
        ContractorDto contractorDto = contractorDtos.stream()
                .filter(c -> c.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Contratista con el nombre " + name + " no existe."));

        return contractorMapper.toContractor(contractorDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContractorDto> findAll() {
        try {
            return contractorMapper.toContractorDtos(contractorRepository.findAll());
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional
    public Contractor save(ContractorDto contractorDto) {
        return contractorRepository.save(contractorMapper.toContractor(contractorDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        try {
            return contractorRepository.existsByName(name);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }
}
