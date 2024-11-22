package com.petrotal.ahcbackend.repository;

import com.petrotal.ahcbackend.entity.AccessHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessHistoryRepository extends JpaRepository<AccessHistory, Long> {
    @Query("select a from AccessHistory a order by a.dateAccess DESC")
    List<AccessHistory> findByOrderByDateAccessDesc();

}