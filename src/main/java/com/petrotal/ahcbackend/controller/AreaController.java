package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.AreaService;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;
    private final AccessHistoryService accessHistoryService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        List<AreaDto> all = areaService.findAll();
        accessHistoryService.logAccessHistory(null, "Consultado la Lista de Áreas");
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
        AreaDto byId = areaService.findById(id);
        accessHistoryService.logAccessHistory(null, "Consultado los Datos del Área con el ID: " + id);
        return new ResponseEntity<>(
                new ResponseDto(
                        byId,
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody AreaDto areaDto) {
        areaService.save(areaDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de un Área");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
