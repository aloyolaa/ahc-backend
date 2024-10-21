package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.repository.DataSignatoryRepository;
import com.petrotal.ahcbackend.service.data.DataSignatoryService;
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
    private final UserService userService;
    //private final DataAccessService dataAccessService;

    @Override
    @Transactional
    public void sign(Long voucherId, String username) {
        User user = userService.findByUsername(username);
        //Data data = dataAccessService.findById(voucherId);

        try {
            dataSignatoryRepository.updateIsSignedByDataAndUser(voucherId, user.getId());
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
