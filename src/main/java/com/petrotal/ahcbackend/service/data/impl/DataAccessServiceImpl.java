package com.petrotal.ahcbackend.service.data.impl;

import com.petrotal.ahcbackend.dto.DataDto;
import com.petrotal.ahcbackend.dto.DataListDto;
import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.entity.DataSignatory;
import com.petrotal.ahcbackend.entity.User;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.exception.ModifiedDataException;
import com.petrotal.ahcbackend.mapper.DataMapper;
import com.petrotal.ahcbackend.repository.DataRepository;
import com.petrotal.ahcbackend.service.data.*;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataAccessServiceImpl implements DataAccessService {
    private final DataRepository dataRepository;
    private final DataMapper dataMapper;
    private final DataSignatoryService dataSignatoryService;
    private final UserService userService;
    private final SignatoryService signatoryService;

    @Override
    @Transactional(readOnly = true)
    public List<Data> findByYear(Integer year) {
        try {
            return dataRepository.findByYear(year - 1);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataListDto> findByFilter(Long areaId, Long contractorId, LocalDate dispatchDateStart, LocalDate dispatchDateEnd, String status) {
        try {
            List<Data> data = null;

            if (areaId == 0 && contractorId > 0) {
                data = dataRepository.findByContractorAndDispatchDateBetweenAndStatus(contractorId, dispatchDateStart, dispatchDateEnd, status);
            } else if (areaId > 0 && contractorId == 0) {
                data = dataRepository.findByAreaAndDispatchDateBetweenAndStatus(areaId, dispatchDateStart, dispatchDateEnd, status);
            } else if (areaId == 0 && contractorId == 0) {
                data = dataRepository.findByDispatchDateBetweenAndStatus(dispatchDateStart, dispatchDateEnd, status);
            } else {
                data = dataRepository.findByAreaAndContractorAndDispatchDateBetweenAndStatus(areaId, contractorId, dispatchDateStart, dispatchDateEnd, status);
            }

            return dataMapper.toDataListDtos(data);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Data findById(Long id) {
        try {
            return dataRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Vale con el ID " + id + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Data findByVoucherNumber(String voucherNumber) {
        try {
            return dataRepository.findByVoucherNumber(voucherNumber)
                    .orElseThrow(() -> new EntityNotFoundException("Vale con el número " + voucherNumber + " no existe."));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
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

            if (data.getId() != null && dataSignatoryService.countSignatories(data.getId()) > 0) {
                throw new ModifiedDataException("El Vale con el número " + data.getVoucherNumber() + " ya tiene firmas y no se puede modificar.");
            }

            data.setStatus("PENDIENTE");
            data.getDataDetails().forEach(dt -> dt.setData(data));
            //data.getDataSignatories().forEach(ds -> ds.setData(data));

            dataRepository.save(data);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    public void saveAll(List<Data> data) {
        try {
            dataRepository.saveAll(data);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al guardar los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByVoucherNumber(String voucherNumber) {
        try {
            return dataRepository.existsByVoucherNumber(voucherNumber);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getNextVoucherNumber() {
        try {
            return dataRepository.findByVoucherNumberNotNullOrderByVoucherNumberDesc() + 1;
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DataListDto> findBySignatory() {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());

        try {
            return dataMapper.toDataListDtos(dataRepository.findPendingVouchersByRole(user.getRole().getName()));
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }

    @Override
    @Transactional
    public void cancelVoucher(Long id) {
        try {
            if (!dataRepository.existsById(id)) {
                throw new EntityNotFoundException("Vale con el ID " + id + " no existe.");
            }

            dataRepository.updateStatusByVoucherId("CANCELADO", id);
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }

    }

    @Override
    @Transactional
    public void sign(Long voucherId) {
        User user = userService.findByUsername(userService.getUsernameFromSecurityContext());

        DataSignatory dataSignatory = new DataSignatory();
        Data data = new Data();
        data.setId(voucherId);
        dataSignatory.setData(data);
        dataSignatory.setUser(user);

        try {
            if (Boolean.FALSE.equals(signatoryService.existsByUser(user.getId()))) {
                throw new EntityNotFoundException("Usted no tiene una firma registrada.");
            }

            dataSignatoryService.save(dataSignatory);

            if (dataSignatoryService.countByDataIdAndUserRoleName(voucherId) == 4) {
                dataRepository.updateStatusByVoucherId("APROBADO", voucherId);
            }
        } catch (DataAccessException | TransactionException e) {
            throw new DataAccessExceptionImpl("Error al acceder a los datos. Inténtelo mas tarde.");
        }
    }
}
