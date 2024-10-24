package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.DataSignatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DataSignatoryRepository extends JpaRepository<DataSignatory, Long> {
    @Transactional
    @Modifying
    @Query("update DataSignatory d set d.isSigned = TRUE where d.data.id = ?1 and d.user.id = ?2")
    void updateIsSignedByDataAndUser(Long dataId, Long userId);

    @Query("select count(d) from DataSignatory d where d.data.id = ?1 and d.isSigned = true")
    long countByDataIdAndIsSignedTrue(Long id);

    @Query("select (count(d) > 0) from DataSignatory d where d.user.id = ?1")
    boolean existsByUserId(Long id);
}