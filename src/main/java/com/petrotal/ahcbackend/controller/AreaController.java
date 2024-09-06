package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.AreaDto;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.data.AreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/area")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("/")
    public ResponseEntity<ResponseDto> getAll() {
        return new ResponseEntity<>(
                new ResponseDto(
                        areaService.findAll(),
                        true)
                , HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(
                new ResponseDto(
                        areaService.findById(id),
                        true)
                , HttpStatus.OK
        );
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> save(@Valid @RequestBody AreaDto areaDto) {
        return new ResponseEntity<>(
                new ResponseDto(
                        areaService.save(areaDto),
                        true)
                , HttpStatus.OK
        );
    }
}
