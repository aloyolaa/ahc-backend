package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Data;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long> {
    boolean existsByVoucherNumber(String voucherNumber);

    @EntityGraph(attributePaths = {"area", "contractor", "equipment", "dataDetails"})
    @Query("select d from Data d where extract(year from d.dispatchDate) = ?1")
    List<Data> findByYear(Integer year);

    @Query("select MAX(d.voucherNumber) from Data d")
    Integer findByVoucherNumberNotNullOrderByVoucherNumberDesc();
}