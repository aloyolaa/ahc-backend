package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.entity.Area;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.mapper.AreaMapper;
import com.petrotal.ahcbackend.repository.AreaRepository;
import com.petrotal.ahcbackend.service.data.AreaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaServiceImpl implements AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    @Override
    @Transactional(readOnly = true)
    public Area findById(Long id) {
        try {
            return areaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Area con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Area findByName(String name) {
        try {
            return areaRepository.findByName(name)
                    .orElseThrow(() -> new EntityNotFoundException("Area con el nombre " + name + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AreaDto> findAll() {
        try {
            return areaMapper.toAreaDtos(areaRepository.findAll());
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    @Transactional
    public Area save(Area area) {
        area.setName(area.getName().toUpperCase());
        return areaRepository.save(area);
    }
}
