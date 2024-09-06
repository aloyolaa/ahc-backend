package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.entity.Equipment;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.EquipmentMapper;
import com.petrotal.ahcbackend.repository.EquipmentRepository;
import com.petrotal.ahcbackend.service.data.EquipmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {
    private final EquipmentRepository equipmentRepository;
    private final EquipmentMapper equipmentMapper;

    @Override
    @Transactional(readOnly = true)
    public EquipmentDto findById(Long id) {
        try {
            Equipment equipment = equipmentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Equipamiento con el ID " + id + " no existe."));
            return equipmentMapper.toEquipmentDto(equipment);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Equipment findByName(String name) {
        try {
            return equipmentRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("Equipamiento con el nombre " + name + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipmentDto> findAll() {
        try {
            return equipmentMapper.toEquipmentDtos(equipmentRepository.findAll());
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional
    public Equipment save(EquipmentDto equipmentDto) {
        return equipmentRepository.save(equipmentMapper.toEquipment(equipmentDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        try {
            return equipmentRepository.existsByName(name);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }
}
