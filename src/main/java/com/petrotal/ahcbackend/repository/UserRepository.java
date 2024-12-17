package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where upper(u.username) = upper(?1)")
    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmailIgnoreCase(String email);

    @Query("select u from User u where u.enabled = true order by u.username")
    List<User> findByEnabledTrueOrderByUsernameAsc();

    @Transactional
    @Modifying
    @Query("update User u set u.enabled = FALSE where upper(u.username) = upper(?1)")
    void updateEnabledByUsernameIgnoreCase(String username);
}