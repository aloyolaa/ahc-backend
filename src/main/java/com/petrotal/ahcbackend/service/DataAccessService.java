package com.petrotal.ahcbackend.service;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;

import java.util.List;

public interface DataAccessService {
    List<Data> findByYear(Integer year);

    void save(DataDto dataDto);

    Boolean existsByVoucherNumber(String voucherNumber);
}
