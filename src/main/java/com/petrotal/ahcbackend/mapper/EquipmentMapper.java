package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Equipment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EquipmentMapper {
    @Mapping(target = "type", expression = "java(equipment.getType().name())")
    EquipmentDto toEquipmentDto(Equipment equipment);

    @Mapping(target = "name", expression = "java(equipmentDto.name().toUpperCase())")
    @Mapping(target = "type", expression = "java(com.petrotal.ahcbackend.enumerator.EquipmentType.valueOf(equipmentDto.type().toUpperCase()))")
    Equipment toEquipment(EquipmentDto equipmentDto);

    List<EquipmentDto> toEquipmentDtos(List<Equipment> equipments);
}
