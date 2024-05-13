package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Equipment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    EquipmentDto toEquipmentDto(Equipment equipment);

    Equipment toEquipment(EquipmentDto equipmentDto);

    List<EquipmentDto> toEquipmentDtos(List<Equipment> equipments);
}
