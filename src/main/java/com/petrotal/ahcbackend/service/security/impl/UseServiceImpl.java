package com.petrotal.ahcbackend.service.security.impl;

import com.petrotal.ahcbackend.dto.UserProfileDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UseServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Username %s no existe en el sistema!", username)));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public UserProfileDto getProfile() {
        return userMapper.toUserProfileDto(findByUsername(getUsernameFromSecurityContext()));
    }

    @Override
    public String getUsernameFromSecurityContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return principal.toString();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserSignatoryDto findByRole(String role) {
        try {
            return userMapper.toUserSignatoryDto(
                    userRepository.findByRoleAndEnabledTrueOrderByHierarchyAsc(role)
                            .orElseThrow(() -> new EntityNotFoundException("No existe un usuario con el rol: " + role + " activo.")));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde." + e.getMessage());
        }
    }

    @Override
    @Transactional
    public User save(UserRegisterDto userRegisterDto) {
        User user = userMapper.toUser(userRegisterDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username) {
        try {
            return userRepository.existsByUsernameIgnoreCase(username);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        try {
            return userRepository.existsByEmailIgnoreCase(email);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }
}
