package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDetailDto;
import com.petrotal.ahcbackend.dto.DataSignatoryDto;
import com.petrotal.ahcbackend.entity.DataDetail;
import com.petrotal.ahcbackend.entity.DataSignatory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DataSignatoryMapper {
    @Mapping(target = "user", source = "user.id")
    DataSignatoryDto toDataSignatoriesDto(DataSignatory dataSignatory);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "data", ignore = true)
    @Mapping(target = "isSigned", ignore = true)
    @Mapping(target = "user.id", source = "user")
    DataSignatory toDatSignatories(DataSignatoryDto dataSignatoryDto);
}
