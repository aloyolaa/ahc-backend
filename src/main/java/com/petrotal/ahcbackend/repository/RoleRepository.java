package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}