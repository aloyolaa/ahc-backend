package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.DataSignatory;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.repository.DataSignatoryRepository;
import com.petrotal.ahcbackend.service.data.DataSignatoryService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.persistence.EntityNotFoundException;
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
    public void sign(Long voucherId) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());


        DataSignatory dataSignatory = new DataSignatory();
        Data data = new Data();
        data.setId(voucherId);
        dataSignatory.setData(data);
        dataSignatory.setUser(user);

        try {
            if (Boolean.FALSE.equals(signatoryService.existsByUser(user.getId()))) {
                throw new EntityNotFoundException("Usted no tiene una firma registrada.");
            }

            dataSignatoryRepository.save(dataSignatory);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public long countSignatories(Long voucherId) {
        return dataSignatoryRepository.countByDataIdAndIsSignedTrue(voucherId);
    }
}
