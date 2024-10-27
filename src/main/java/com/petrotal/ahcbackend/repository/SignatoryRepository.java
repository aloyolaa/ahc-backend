package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Signatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SignatoryRepository extends JpaRepository<Signatory, Long> {
  @Query("select s from Signatory s where s.user.id = ?1")
  Optional<Signatory> findByUserId(Long id);

  @Query("select (count(s) > 0) from Signatory s where s.user.id = ?1")
  boolean existsByUser_Id(Long id);
}