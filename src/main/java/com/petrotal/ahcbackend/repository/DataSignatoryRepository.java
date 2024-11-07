package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.DataSignatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DataSignatoryRepository extends JpaRepository<DataSignatory, Long> {
    @Query("select count(d) from DataSignatory d where d.data.id = ?1")
    long countByDataIdAndIsSignedTrue(Long id);

    @Query("select (count(d) > 0) from DataSignatory d where d.user.id = ?1")
    boolean existsByUserId(Long id);

    @Query("select count(d) from DataSignatory d where d.data.id = ?1 and (d.user.role.name = 'FIELD_MANAGER' or d.user.role.name = 'LOGISTICS_COORDINATOR' or d.user.role.name = 'PRODUCTION_SUPERINTENDENT' or d.user.role.name = 'STORE')")
    long countByDataIdAndUserRoleName(Long id);
}