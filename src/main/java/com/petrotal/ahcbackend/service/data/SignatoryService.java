package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.SignatoryDto;
import com.petrotal.ahcbackend.entity.Signatory;
import org.springframework.web.multipart.MultipartFile;

public interface SignatoryService {
    void save(MultipartFile signatureFile);

    SignatoryDto getByUser();

    Signatory updateSignature(Long id, MultipartFile signatureFile);
}
