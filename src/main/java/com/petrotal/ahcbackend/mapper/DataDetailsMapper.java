package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDetailDto;
import com.petrotal.ahcbackend.entity.DataDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataDetailsMapper {
    DataDetailDto toDataDetailsDto(DataDetail dataDetail);

    @Mapping(target = "data", ignore = true)
    @Mapping(target = "description", expression = "java(com.petrotal.ahcbackend.enumerator.FuelType.valueOf(dataDetailDto.description().toUpperCase()))")
    @Mapping(target = "location", expression = "java(dataDetailDto.location().toUpperCase())")
    @Mapping(target = "unitOfMeasurement", expression = "java(dataDetailDto.unitOfMeasurement().toUpperCase())")
    DataDetail toDataDetails(DataDetailDto dataDetailDto);
}
