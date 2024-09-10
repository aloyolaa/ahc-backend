package com.petrotal.ahcbackend.service.security;

import com.petrotal.ahcbackend.entity.User;

public interface UserService {
    User findById(Long id);

    User findByUsername(String username);

    User save(User user);
}
