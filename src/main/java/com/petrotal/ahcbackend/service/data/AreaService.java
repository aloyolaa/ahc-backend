package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.entity.Area;

import java.util.List;

public interface AreaService {
    AreaDto findById(Long id);

    Area findByName(String name);

    List<AreaDto> findAll();

    Area save(AreaDto areaDto);

    Boolean existsByName(String name);
}
