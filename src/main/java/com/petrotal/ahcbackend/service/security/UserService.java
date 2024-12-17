package com.petrotal.ahcbackend.service.security;

import com.petrotal.ahcbackend.dto.UserListDto;
import com.petrotal.ahcbackend.dto.UserProfileDto;
import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.dto.UserUpdateDto;
import com.petrotal.ahcbackend.entity.User;

import java.util.List;

public interface UserService {
    List<UserListDto> findAllEnabled();

    User findById(Long id);

    User findByUsername(String username);

    UserProfileDto getProfile();

    String getUsernameFromSecurityContext();

    User save(UserRegisterDto userRegisterDto);

    User update(UserUpdateDto userUpdateDto, String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void delete(String username);
}
