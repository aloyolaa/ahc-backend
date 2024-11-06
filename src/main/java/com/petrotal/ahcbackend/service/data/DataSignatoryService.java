package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.entity.DataSignatory;

public interface DataSignatoryService {
    void save(DataSignatory dataSignatory);

    long countSignatories(Long voucherId);

    long countByDataIdAndUserRoleName(Long voucherId);
}
