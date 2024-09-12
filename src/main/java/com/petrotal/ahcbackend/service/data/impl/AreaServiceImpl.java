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
    public AreaDto findById(Long id) {
        try {
            Area area = areaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Area con el ID " + id + " no existe."));
            return areaMapper.toAreaDto(area);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }

    @Override
    public Area findByName(String name, List<AreaDto> areaDtos) {
        AreaDto areaDto = areaDtos.stream()
                .filter(a -> a.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Area con el nombre " + name + " no existe."));

        return areaMapper.toArea(areaDto);
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
    public Area save(AreaDto areaDto) {
        return areaRepository.save(areaMapper.toArea(areaDto));
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByName(String name) {
        try {
            return areaRepository.existsByName(name);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.", e);
        }
    }
}
