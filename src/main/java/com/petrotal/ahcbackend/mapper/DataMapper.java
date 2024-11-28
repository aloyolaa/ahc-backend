package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.DataListDto;
import com.petrotal.ahcbackend.dto.DataViewDto;
import com.petrotal.ahcbackend.dto.UserSignatoryDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.DataSignatory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DataDetailsMapper.class, UserMapper.class})
public interface DataMapper {
    @Mapping(target = "area", source = "area.id")
    @Mapping(target = "contractor", source = "contractor.id")
    @Mapping(target = "equipment", source = "equipment.id")
    @Mapping(target = "details", source = "dataDetails")
    @Mapping(target = "signatories", source = "dataSignatories")
    DataDto toDataDto(Data data);

    @Mapping(target = "area", source = "area.name")
    @Mapping(target = "contractor", source = "contractor.name")
    @Mapping(target = "equipment", source = "equipment.name")
    @Mapping(target = "details", source = "dataDetails")
    @Mapping(target = "signatories", source = "dataSignatories")
    DataViewDto toDataViewDto(Data data);

    @Mapping(target = "area.id", source = "area")
    @Mapping(target = "contractor.id", source = "contractor")
    @Mapping(target = "equipment.id", source = "equipment")
    @Mapping(target = "usageDetail", expression = "java(dataDto.usageDetail().toUpperCase())")
    @Mapping(target = "materialStatus", expression = "java(dataDto.materialStatus().toUpperCase())")
    @Mapping(target = "dataDetails", source = "details")
    @Mapping(target = "dataSignatories", ignore = true)
    Data toData(DataDto dataDto);

    List<DataListDto> toDataListDtos(List<Data> data);

    default List<UserSignatoryDto> mapDataSignatoriesToUserSignatoryDto(List<DataSignatory> dataSignatories) {
        return dataSignatories.stream()
                .map(dataSignatory -> new UserSignatoryDto(
                                dataSignatory.getUser().getFirstName(),
                                dataSignatory.getUser().getLastName(),
                                dataSignatory.getUser().getRole().getName()
                        )
                )
                .toList();
    }
}
