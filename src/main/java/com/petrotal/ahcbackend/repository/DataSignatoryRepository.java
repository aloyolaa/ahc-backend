package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.DataSignatory;
import com.petrotal.ahcbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DataSignatoryRepository extends JpaRepository<DataSignatory, Long> {
    @Transactional
    @Modifying
    @Query("update DataSignatory d set d.isSigned = TRUE where d.data = ?1 and d.user = ?2")
    void updateIsSignedByDataAndUser(Data data, User user);
}