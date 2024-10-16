package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;

import java.util.List;

public interface DataAccessService {
    List<Data> findByYear(Integer year);

    void save(DataDto dataDto);

    void saveAll(List<Data> data);

    Boolean existsByVoucherNumber(String voucherNumber);
}
