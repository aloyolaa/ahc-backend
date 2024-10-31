package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.service.security.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAll() {
        return new ResponseEntity<>(
                new ResponseDto(
                        roleService.getAll(),
                        true)
                , HttpStatus.OK
        );
    }
}
