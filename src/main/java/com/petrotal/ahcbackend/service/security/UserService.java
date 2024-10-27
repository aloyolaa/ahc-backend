package com.petrotal.ahcbackend.service.security;

import com.petrotal.ahcbackend.dto.UserProfileDto;
import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.dto.UserSignatoryDto;
import com.petrotal.ahcbackend.entity.User;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    UserProfileDto getProfile();

    String getUsernameFromSecurityContext();

    UserSignatoryDto findByRole(String role);

    User save(UserRegisterDto userRegisterDto);
}
