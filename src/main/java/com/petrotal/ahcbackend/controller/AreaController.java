package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getAll() {
        return new ResponseEntity<>(
                new ResponseDto(
                        areaService.findAll(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseDto(
                        areaService.findById(id),
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('REGISTER')")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody AreaDto areaDto) {
        areaService.save(areaDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK
        );
    }
}
