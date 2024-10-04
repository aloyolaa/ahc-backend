package com.petrotal.ahcbackend.service.security.impl;

import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.dto.UserSignatoryDto;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.UserMapper;
import com.petrotal.ahcbackend.repository.UserRepository;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UseServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Username %s no existe en el sistema!", username)));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSignatoryDto findByRole(String role) {
        try {
            return userMapper.toUserSignatoryDto(
                    userRepository.findByRoleAndEnabledTrueOrderByHierarchyAsc(role)
                            .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con el rol: " + role + ".")));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde." + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public User save(UserRegisterDto userRegisterDto) {
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(userMapper.toUser(userRegisterDto));
    }
}
