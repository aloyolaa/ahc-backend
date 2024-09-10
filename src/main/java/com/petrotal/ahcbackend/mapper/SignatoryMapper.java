package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.SignatoryDto;
import com.petrotal.ahcbackend.entity.Signatory;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SignatoryMapper {
    SignatoryDto toDto(Signatory signatory);

    Signatory toEntity(SignatoryDto signatoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Signatory partialUpdate(SignatoryDto signatoryDto, @MappingTarget Signatory signatory);
}