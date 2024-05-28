package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.DataMapper;
import com.petrotal.ahcbackend.repository.DataRepository;
import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.service.data.ContractorService;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.data.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DataAccessServiceImpl implements DataAccessService {
    private final DataRepository dataRepository;
    private final DataMapper dataMapper;
    private final AreaService areaService;
    private final ContractorService contractorService;
    private final EquipmentService equipmentService;

    @Override
    @Transactional(readOnly = true)
    public List<Data> findByYear(Integer year) {
        try {
            return dataRepository.findByYear(year - 1);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional
    public void save(DataDto dataDto) {
        try {
            Data data = dataMapper.toData(dataDto);
            /*data.setArea(areaService.findById(data.getArea().getId()));
            data.setContractor(contractorService.findById(data.getContractor().getId()));
            data.setEquipment(equipmentService.findById(data.getEquipment().getId()));*/
            dataRepository.save(data);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    public void saveAll(List<Data> data) {
        try {
            dataRepository.saveAll(data);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByVoucherNumber(String voucherNumber) {
        try {
            return dataRepository.existsByVoucherNumber(voucherNumber);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }
}
