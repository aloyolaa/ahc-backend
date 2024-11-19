package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.Data;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
            select d from Data d
            where d.status = 'PENDIENTE'
            and d.id not in (
                  select ds.data.id
                  from DataSignatory ds
                  join ds.user u
                  join u.role r
                  where r.name = ?1
              )
            """)
    List<Data> findPendingVouchersByRole(String role);

    @Transactional
    @Modifying
    @Query("update Data d set d.status = ?1 where d.id = ?2")
    void updateStatusByVoucherId(String status, Long id);

    @Query("""
            select d from Data d
            where d.area.id = ?1 and d.contractor.id = ?2 and d.dispatchDate between ?3 and ?4 and upper(d.status) = upper(?5)""")
    List<Data> findByAreaAndContractorAndDispatchDateBetweenAndStatus(Long areaId, Long contractorId, LocalDate dispatchDateStart, LocalDate dispatchDateEnd, String status);
}