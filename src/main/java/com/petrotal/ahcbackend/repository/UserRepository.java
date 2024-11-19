package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where upper(u.username) = upper(?1)")
    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    @Query("select u from User u where u.role.name = ?1 and u.enabled = true")
    Optional<User> findByRoleAndEnabledTrueOrderByHierarchyAsc(String name);
}