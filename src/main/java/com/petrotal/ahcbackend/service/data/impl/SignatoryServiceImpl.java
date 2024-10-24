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
        Signatory signatory = new Signatory();
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSignature(signaturePath);
        signatory.setUser(user);
        signatoryRepository.save(signatory);
    }

    @Override
    @Transactional(readOnly = true)
    public SignatoryDto getByUser() {
        String username = userService.getUsernameFromSecurityContext();
        User user = userService.findByUsername(username);

        try {
            Signatory signatory = signatoryRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario con el username " + username + " no tiene una firma asignada."));

            String signatoryFile = Base64.getEncoder().encodeToString(fileStorageService.loadFileAsResource(signatory.getSignature()));

            return new SignatoryDto(signatory.getId(), signatoryFile);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional
    public void updateSignature(MultipartFile signatureFile) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());
        Signatory signatory = signatoryRepository.findById(user.getId()).orElseThrow(() -> new EntityNotFoundException("Signatario con el ID " + user.getId() + " no existe."));
        String previousSignature = signatory.getSignature();
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSignature(signaturePath);
        fileStorageService.deleteFile(previousSignature);
        signatoryRepository.save(signatory);
    }
}
