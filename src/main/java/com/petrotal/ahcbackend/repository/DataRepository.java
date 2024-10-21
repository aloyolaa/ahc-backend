package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Data;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface DataRepository extends JpaRepository<Data, Long> {
    boolean existsByVoucherNumber(String voucherNumber);

    @EntityGraph(attributePaths = {"area", "contractor", "equipment", "dataDetails"})
    @Query("select d from Data d where extract(year from d.dispatchDate) = ?1")
    List<Data> findByYear(Integer year);

    @Query("select d from Data d where d.voucherNumber = ?1")
    Optional<Data> findByVoucherNumber(String voucherNumber);

    @Query("select MAX(d.voucherNumber) from Data d")
    Integer findByVoucherNumberNotNullOrderByVoucherNumberDesc();

    @Query("""
            select d from Data d inner join d.dataSignatories dataSignatories
            where dataSignatories.user.username = ?1
            order by d.dispatchDate DESC""")
    List<Data> findByDataSignatoriesUserIdOrderByDispatchDateDesc(String username);

    @Transactional
    @Modifying
    @Query("update Data d set d.status = 'CANCELADO' where d.voucherNumber = ?1")
    void updateStatusByVoucherNumber(String voucherNumber);


}