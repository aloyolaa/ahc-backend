package com.petrotal.ahcbackend.service;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.entity.Contractor;

import java.util.List;

public interface ContractorService {
    Contractor findById(Long id);

    List<ContractorDto> findAll();
}
