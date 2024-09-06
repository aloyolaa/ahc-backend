package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.ContractorDto;
import com.petrotal.ahcbackend.entity.Contractor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContractorMapper {
    ContractorDto toContractorDto(Contractor contractor);

    @Mapping(target = "name", expression = "java(contractorDto.name().toUpperCase())")
    Contractor toContractor(ContractorDto contractorDto);

    List<ContractorDto> toContractorDtos(List<Contractor> contractors);
}
