package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.entity.Area;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaDto toAreaDto(Area area);

    @Mapping(target = "name", expression = "java(areaDto.name().toUpperCase())")
    Area toArea(AreaDto areaDto);

    List<AreaDto> toAreaDtos(List<Area> areas);
}
