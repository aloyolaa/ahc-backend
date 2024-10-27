package com.petrotal.ahcbackend.mapper;

import com.petrotal.ahcbackend.dto.UserProfileDto;
import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.dto.UserSignatoryDto;
import com.petrotal.ahcbackend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "firstName", expression = "java(userRegisterDto.firstName().toUpperCase())")
    @Mapping(target = "lastName", expression = "java(userRegisterDto.lastName().toUpperCase())")
    @Mapping(target = "role.id", source = "role")
    User toUser(UserRegisterDto userRegisterDto);

    @Mapping(target = "role", source = "role.name")
    UserSignatoryDto toUserSignatoryDto(User user);

    @Mapping(target = "role", source = "role.name")
    UserProfileDto toUserProfileDto(User user);
}
