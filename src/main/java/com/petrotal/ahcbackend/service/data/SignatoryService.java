package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.SignatoryDto;
import org.springframework.web.multipart.MultipartFile;

public interface SignatoryService {
    void save(MultipartFile signatureFile);

    SignatoryDto getByUser();

    void updateSignature(MultipartFile signatureFile);
}
