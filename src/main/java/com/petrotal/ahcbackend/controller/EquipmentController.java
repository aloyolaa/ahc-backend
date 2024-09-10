package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.EquipmentDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.EquipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
@RequiredArgsConstructor
public class EquipmentController {
    private final EquipmentService equipmentService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        return new ResponseEntity<>(
                new ResponseDto(
                        equipmentService.findAll(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseDto(
                        equipmentService.findById(id),
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody EquipmentDto equipmentDto) {
        return new ResponseEntity<>(
                new ResponseDto(
                        equipmentService.save(equipmentDto),
                        true)
                , HttpStatus.OK
        );
    }
}
