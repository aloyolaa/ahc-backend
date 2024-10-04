package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.SignatoryDto;
import com.petrotal.ahcbackend.entity.Signatory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SignatoryMapper {
    SignatoryDto toSignatoryDto(Signatory signatory);

    Signatory toSignatory(SignatoryDto signatoryDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Signatory partialUpdate(SignatoryDto signatoryDto, @MappingTarget Signatory signatory);
}