package com.petrotal.ahcbackend.service.security;

import com.petrotal.ahcbackend.entity.AccessHistory;

import java.util.List;

public interface AccessHistoryService {
    List<AccessHistory> getAll(Integer month);

    void save(AccessHistory accessHistory);

    String getAccessHistory(List<AccessHistory> histories);

    void logAccessHistory(String username, String action);
}
