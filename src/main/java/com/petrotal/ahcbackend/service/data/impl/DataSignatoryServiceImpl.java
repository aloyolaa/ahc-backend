package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.entity.DataSignatory;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.repository.DataSignatoryRepository;
import com.petrotal.ahcbackend.service.data.DataSignatoryService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DataSignatoryServiceImpl implements DataSignatoryService {
    private final DataSignatoryRepository dataSignatoryRepository;
    private final SignatoryService signatoryService;
    private final UserService userService;

    @Override
    @Transactional
    public void save(DataSignatory dataSignatory) {
        try {
            dataSignatoryRepository.save(dataSignatory);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countSignatories(Long voucherId) {
        return dataSignatoryRepository.countByDataIdAndIsSignedTrue(voucherId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByDataIdAndUserRoleName(Long voucherId) {
        return dataSignatoryRepository.countByDataIdAndUserRoleName(voucherId);
    }
}
