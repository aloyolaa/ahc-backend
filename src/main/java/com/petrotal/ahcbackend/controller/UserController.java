package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.dto.UserRegisterDto;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AccessHistoryService accessHistoryService;

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        userService.save(userRegisterDto);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER', 'FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getProfile() {
        return new ResponseEntity<>(
                new ResponseDto(
                        userService.getProfile(),
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/access-history")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAccessHistory() {
        return new ResponseEntity<>(
                new ResponseDto(
                        accessHistoryService.getAccessHistory(accessHistoryService.getAll()),
                        true)
                , HttpStatus.OK);
    }
}
