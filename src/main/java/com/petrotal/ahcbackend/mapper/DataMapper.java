package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataMapper {
    @Mapping(target = "area", source = "area.id")
    @Mapping(target = "contractor", source = "contractor.id")
    @Mapping(target = "equipment", source = "equipment.id")
    DataDto toDataDto(Data data);

    @Mapping(target = "unitOfMeasurement", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "area.id", source = "area")
    @Mapping(target = "contractor.id", source = "contractor")
    @Mapping(target = "equipment.id", source = "equipment")
    @Mapping(target = "description", expression = "java(com.petrotal.ahcbackend.enumerator.FuelType.valueOf(dataDto.description().toUpperCase()))")
    Data toData(DataDto dataDto);
}
