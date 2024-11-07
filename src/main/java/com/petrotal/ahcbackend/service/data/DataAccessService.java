package com.petrotal.ahcbackend.service.data;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.DataListDto;
import com.petrotal.ahcbackend.entity.Data;

import java.time.LocalDate;
import java.util.List;

public interface DataAccessService {
    List<Data> findByYear(Integer year);

    List<DataListDto> findByFilter(Long areaId, Long contractorId, LocalDate dispatchDateStart, LocalDate dispatchDateEnd, String status);

    Data findById(Long id);

    DataDto findByVoucherNumber(String voucherNumber);

    void save(DataDto dataDto);

    void saveAll(List<Data> data);

    Boolean existsByVoucherNumber(String voucherNumber);

    int getNextVoucherNumber();

    List<DataListDto> findBySignatory();

    void cancelVoucher(Long id);

    void sign(Long voucherId);
}
