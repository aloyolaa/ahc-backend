package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.EquipmentService;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;
    private final AccessHistoryService accessHistoryService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<EquipmentDto> all = equipmentService.findAll();
        accessHistoryService.logAccessHistory(null, "Consultado la Lista de Equipamientos");
        return new ResponseEntity<>(
                new ResponseDto(
                        all,
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        EquipmentDto byId = equipmentService.findById(id);
        accessHistoryService.logAccessHistory(null, "Consultado los Datos del Equipamiento con el ID: " + id);
        return new ResponseEntity<>(
                new ResponseDto(
                        byId,
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody EquipmentDto equipmentDto) {
        equipmentService.save(equipmentDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de un Equipamiento");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
