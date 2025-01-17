package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.*;
import com.petrotal.ahcbackend.service.security.AccessHistoryService;
import com.petrotal.ahcbackend.service.security.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AccessHistoryService accessHistoryService;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAll() {
        List<UserListDto> allEnabled = userService.findAllEnabled();
        accessHistoryService.logAccessHistory(null, "Consultado la Lista de Usuarios Habilitados");
        return new ResponseEntity<>(
                new ResponseDto(
                        allEnabled,
                        true)
                , HttpStatus.OK);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        userService.save(userRegisterDto);
        accessHistoryService.logAccessHistory(null, "Guardado Datos de un Usuario");
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos guardados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @PutMapping("/update/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable String username) {
        userService.update(userUpdateDto, username);
        accessHistoryService.logAccessHistory(null, "Actualizado Datos de un Usuario con el username: " + username);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Datos actualizados correctamente.",
                        true)
                , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> delete(@PathVariable String username) {
        userService.delete(username);
        accessHistoryService.logAccessHistory(null, "Deshabilitado al Usuario con el username: " + username);
        return new ResponseEntity<>(
                new ResponseDto(
                        "Usuario inhabilitado.",
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'REGISTER', 'FIELD_MANAGER', 'LOGISTICS_COORDINATOR', 'PRODUCTION_SUPERINTENDENT', 'STORE')")
    public ResponseEntity<ResponseDto> getProfile() {
        UserProfileDto profile = userService.getProfile();
        accessHistoryService.logAccessHistory(null, "Consultado los Datos del Usuario con el Username: " + profile.username());
        return new ResponseEntity<>(
                new ResponseDto(
                        profile,
                        true)
                , HttpStatus.OK);
    }

    @GetMapping("/access-history/{month}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDto> getAccessHistory(@PathVariable Integer month) {
        String accessHistory = accessHistoryService.getAccessHistory(accessHistoryService.getAll(month));
        accessHistoryService.logAccessHistory(null, "Consultado el Archivo de Historial de Accesos del Mes: " + month);
        return new ResponseEntity<>(
                new ResponseDto(
                        accessHistory,
                        true)
                , HttpStatus.OK);
    }
}
