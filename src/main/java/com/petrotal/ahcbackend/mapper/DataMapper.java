package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataMapper {
    @Mapping(target = "unitOfMeasurement", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "area.id", source = "area")
    @Mapping(target = "contractor.id", source = "contractor")
    @Mapping(target = "equipment.id", source = "equipment")
    Data toData(DataDto dataDto);
}
