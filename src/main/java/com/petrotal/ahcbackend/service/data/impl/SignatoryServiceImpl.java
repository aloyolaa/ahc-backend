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
    public Signatory save(Long userId, MultipartFile sealFile, MultipartFile signatureFile) {
        User user = userService.findById(userId);
        Signatory signatory = new Signatory();
        String sealPath = fileStorageService.storeFile(sealFile);
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSeal(sealPath);
        signatory.setSignature(signaturePath);
        signatory.setUser(user);
        return signatoryRepository.save(signatory);
    }

    @Override
    @Transactional(readOnly = true)
    public SignatoryDto getByUserId(Long userId) {
        User user = userService.findById(userId);

        try {
            Signatory signatory = signatoryRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new EntityNotFoundException("El usuario con el ID " + userId + " no tiene un sello o firma asignado."));

            String sealFile = Base64.getEncoder().encodeToString(fileStorageService.loadFileAsResource(signatory.getSeal()));
            String signatoryFile = Base64.getEncoder().encodeToString(fileStorageService.loadFileAsResource(signatory.getSignature()));

            return new SignatoryDto(signatory.getId(), sealFile, signatoryFile);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional
    public Signatory updateSeal(Long id, MultipartFile sealFile) {
        Signatory signatory = signatoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Signatario con el ID " + id + " no existe."));
        String previousSeal = signatory.getSeal();
        String sealPath = fileStorageService.storeFile(sealFile);
        signatory.setSeal(sealPath);
        fileStorageService.deleteFile(previousSeal);
        return signatoryRepository.save(signatory);
    }

    @Override
    @Transactional
    public Signatory updateSignature(Long id, MultipartFile signatureFile) {
        Signatory signatory = signatoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Signatario con el ID " + id + " no existe."));
        String previousSignature = signatory.getSignature();
        String signaturePath = fileStorageService.storeFile(signatureFile);
        signatory.setSignature(signaturePath);
        fileStorageService.deleteFile(previousSignature);
        return signatoryRepository.save(signatory);
    }
}
