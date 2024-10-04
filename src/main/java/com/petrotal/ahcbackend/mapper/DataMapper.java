package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DataDetailsMapper.class, DataSignatoryMapper.class})
public interface DataMapper {
    @Mapping(target = "area", source = "area.id")
    @Mapping(target = "contractor", source = "contractor.id")
    @Mapping(target = "equipment", source = "equipment.id")
    @Mapping(target = "details", source = "dataDetails")
    @Mapping(target = "signatories", source = "dataSignatories")
    DataDto toDataDto(Data data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "area.id", source = "area")
    @Mapping(target = "contractor.id", source = "contractor")
    @Mapping(target = "equipment.id", source = "equipment")
    @Mapping(target = "materialStatus", expression = "java(dataDto.materialStatus().toUpperCase())")
    @Mapping(target = "dataDetails", source = "details")
    @Mapping(target = "dataSignatories", source = "signatories")
    Data toData(DataDto dataDto);
}
