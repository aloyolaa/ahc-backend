package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.SignatoryDto;
import com.petrotal.ahcbackend.entity.Signatory;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.repository.SignatoryRepository;
import com.petrotal.ahcbackend.service.data.FileStorageService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignatoryServiceImpl implements SignatoryService {
    private final SignatoryRepository signatoryRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Override
    @Transactional
    public void save(MultipartFile signatureFile) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());
        Optional<Signatory> optionalSignatory = signatoryRepository.findByUserId(user.getId());

        Signatory signatory;
        String signaturePath = fileStorageService.storeFile(signatureFile);

        if (optionalSignatory.isPresent()) {
            // Actualizar firma existente
            signatory = optionalSignatory.orElseThrow();
            String previousSignature = signatory.getSignature();
            signatory.setSignature(signaturePath);
            fileStorageService.deleteFile(previousSignature);
        } else {
            signatory = new Signatory();
            signatory.setSignature(signaturePath);
            signatory.setUser(user);
        }

        signatoryRepository.save(signatory);
    }

    /*@Override
    @Transactional
    public void save(MultipartFile signatureFile) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());
        if (signatoryRepository.existsByUser_Id(user.getId())) {
            throw new DataAccessExceptionImpl("Ya tiene un firma registrada.");
        }

        Signatory signatory = new Signatory();
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSignature(signaturePath);
        signatory.setUser(user);
        signatoryRepository.save(signatory);
    }

    @Override
    @Transactional
    public void update(MultipartFile signatureFile) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());
        Signatory signatory = signatoryRepository.findByUserId(user.getId()).orElseThrow(() -> new EntityNotFoundException("El usuario con el username " + user.getUsername() + " no tiene una firma asignada."));
        String previousSignature = signatory.getSignature();
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSignature(signaturePath);
        fileStorageService.deleteFile(previousSignature);
        signatoryRepository.save(signatory);
    }*/

    @Override
    @Transactional(readOnly = true)
    public SignatoryDto getByUser(String username) {
        User user = userService.findByUsername(username);

        try {
            Signatory signatory = signatoryRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario con el username " + username + " no tiene una firma asignada."));
            String signatoryFile = Base64.getEncoder().encodeToString(fileStorageService.loadFileAsResource(signatory.getSignature()));

            return new SignatoryDto(signatory.getId(), signatoryFile);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByUser(Long userId) {
        return signatoryRepository.existsByUser_Id(userId);
    }
}
