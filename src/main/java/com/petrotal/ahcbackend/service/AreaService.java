package com.petrotal.ahcbackend.service;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.entity.Area;

import java.util.List;

public interface AreaService {
    Area findById(Long id);

    List<AreaDto> findAll();
}
