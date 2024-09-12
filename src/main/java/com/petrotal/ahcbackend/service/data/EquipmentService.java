package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Equipment;

import java.util.List;

public interface EquipmentService {
    EquipmentDto findById(Long id);

    Equipment findByName(String name, List<EquipmentDto> equipmentDtos);

    List<EquipmentDto> findAll();

    Equipment save(EquipmentDto equipmentDto);

    Boolean existsByName(String name);
}
