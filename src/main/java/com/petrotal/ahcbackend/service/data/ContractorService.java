package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.entity.Contractor;

import java.util.List;

public interface ContractorService {
    Contractor findById(Long id);

    Contractor findByName(String name);

    List<ContractorDto> findAll();
}